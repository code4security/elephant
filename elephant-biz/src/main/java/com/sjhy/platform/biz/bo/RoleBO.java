package com.sjhy.platform.biz.bo;

import com.sjhy.platform.client.dto.config.AppConfig;
import com.sjhy.platform.client.dto.exception.*;
import com.sjhy.platform.client.dto.utils.StringUtils;
import com.sjhy.platform.client.dto.vo.PlayerRoleVO;
import com.sjhy.platform.client.dto.game.Game;
import com.sjhy.platform.client.dto.player.Player;
import com.sjhy.platform.client.dto.player.PlayerRole;
import com.sjhy.platform.persist.mysql.game.GameMapper;
import com.sjhy.platform.persist.mysql.player.PlayerMapper;
import com.sjhy.platform.persist.mysql.player.PlayerRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

import static java.lang.System.getProperty;

@Service
public class RoleBO {
    @Resource
    private PlayerRoleMapper playerRoleMapper;
    @Resource
    private GameMapper gameMapper;
    @Resource
    private PlayerMapper playerMapper;

    public static String ServerID = "";

    /**
     * 创建游戏角色第一步
     * @param playerId
     * @param gameId
     * @param roleName
     * @param deviceToken
     * @return
     * @throws NoSuchRoleException
     * @throws AlreadyExistsPlayerRoleException
     * @throws AdmiralNameIsNotNullableException
     * @throws AdmiralNameIsTooLongException
     * @throws AdmiralNameIncludeHarmonyException
     * @throws AdmiralNameCoincideException
     * @throws CreateRoleException
     * @throws GameIdIsNotExsitsException
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public PlayerRoleVO createNewPlayer(long playerId, String gameId, String roleName, String deviceToken)
            throws NoSuchRoleException, AlreadyExistsPlayerRoleException, AdmiralNameIsNotNullableException,
            AdmiralNameIsTooLongException, AdmiralNameIncludeHarmonyException, AdmiralNameCoincideException, CreateRoleException, GameIdIsNotExsitsException
    {
        // 1.入参校验
        System.out.println("chuangjianjuesexinxi=====================================[][]][][][][][]][][][][][][]");
        // 1.1.角色id不合法
        if(playerId <= 0 || gameId == null){
            throw new NoSuchRoleException();
        }
        // 缓存（注释）
        // 1.1验证gameId是否正确
        Game game = gameMapper.selectByGameId(gameId);
        if (game == null){
            throw new GameIdIsNotExsitsException();
        }
        // 1.2.玩家如果已经存在则直接返回错误信息
        PlayerRole playerRole = playerRoleMapper.selectByPlayerId(gameId,playerId);
        if(playerRole != null){
            throw new AlreadyExistsPlayerRoleException();
        }
        // 2.创建玩家角色信息
        PlayerRole role = createPlayerRole(playerId, gameId, roleName);

        if(role == null || role.getRoleId() == null){
            throw new CreateRoleException();
        }
        // 缓存（注释）
        // playerRoleVO = playerRoleService.getByPrimaryKey(role.getRoleId());
        // 埋点(注释)
        // logService.setRoleBuildLog(playerRoleVO);
        return (PlayerRoleVO) role;
    }

    /**
     * 创建游戏角色第二步
     * @param playerId
     * @param gameId
     * @param roleName
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public PlayerRole createPlayerRole(long playerId, String gameId, String roleName){
        Date now = new Date();
        System.out.println("role==============================>playerId+"
                +playerId+";gameid+"+gameId+";rolename+"+roleName+";roleAvatar");
        PlayerRole role = new PlayerRole();

        role.setPlayerId(playerId);
        role.setGameId(gameId);
        role.setRoleName(roleName);
        role.setCreateTime(now);
        role.setLastLoginTime(now);

        role.setFriendid(0);
        role.setIsDeleted((byte)0);
        role.setRegFriend((byte)0);
        role.setMinute(0L);
        role.setAdtime(null);
        role.setViptime(null);
        role.setCurrency(0);

        role.setRoleId(Long.valueOf((playerRoleMapper.countByRole(role)+1)));

        // 缓存（注释）
        Player players = new Player();
        players.setPlayerId(playerId);
        Player player = playerMapper.selectByPlayerId(players);
        if (player != null){
            role.setChannelId(player.getChannelId());
        }

        playerRoleMapper.insert(role);

        return role;
    }

    /**
     * 更新指定玩家的最后登录时间
     * @param roleId
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public void updateLastLoginTime(long roleId){
        PlayerRole record = new PlayerRole();
        record.setRoleId(roleId);
        record.setLastLoginTime(Calendar.getInstance().getTime());
        // 上传最后一次登录的服务器ID（修改，待验证）
        if("".equals(ServerID)){
            ServerID = getProperty(AppConfig.SERVER_ID, "");
        }
        record.setLastLoginServer(StringUtils.getInt(ServerID));
        playerRoleMapper.updateByPrimaryKeySelective(record);
    }
}
