package com.sjhy.platform.biz.bo;

import com.sjhy.platform.biz.redis.RedisServiceImpl;
import com.sjhy.platform.biz.redis.redisVo.redisCont.KrGlobalCache;
import com.sjhy.platform.biz.redis.redisVo.redisCont.PlayerRoleCache;
import com.sjhy.platform.biz.utils.DbVerifyUtils;
import com.sjhy.platform.biz.utils.GetBeanHelper;
import com.sjhy.platform.biz.utils.ShieldingWordsUtil;
import com.sjhy.platform.biz.utils.StringUtils;
import com.sjhy.platform.client.deploy.config.AppConfig;
import com.sjhy.platform.client.deploy.exception.*;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.dto.game.Game;
import com.sjhy.platform.client.dto.player.PlayerRole;
import com.sjhy.platform.client.dto.vo.AccountVO;
import com.sjhy.platform.persist.mysql.player.PlayerRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class RoleBO {
    @Resource
    private PlayerRoleMapper playerRoleMapper;
    @Autowired
    private ShieldingWordsUtil shieldingWordsUtil;
    @Resource
    private RedisServiceImpl redisService;
    @Resource
    private KrGlobalCache krGlobalCache;
    @Resource
    private PlayerRoleCache playerRoleCache;
    @Resource
    private DbVerifyUtils dbVerifyUtils;

    public static String ServerID = "";

    /**
     * 创建游戏角色第一步
     * @param roleName
     * @param deviceToken
     * @return
     * @throws NoSuchRoleException
     * @throws AlreadyExistsPlayerRoleException
     * @throws CreateRoleException
     * @throws GameIdIsNotExsitsException
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public PlayerRole createNewPlayer(ServiceContext sc, String roleName, String deviceToken)
            throws NoSuchRoleException, AlreadyExistsPlayerRoleException, CreateRoleException, GameIdIsNotExsitsException
    {
        // 1.入参校验
        System.out.println("chuangjianjuesexinxi=====================================[][]][][][][][]][][][][][][]");
        // 1.1.角色id不合法
        if(sc.getPlayerId() <= 0 || sc.getGameId() == null){
            throw new NoSuchRoleException();
        }
        // 缓存
        // 1.1验证gameId是否正确
        Game game = krGlobalCache.getGameDicById(Integer.valueOf(sc.getGameId()));
        if (game == null){
            throw new GameIdIsNotExsitsException();
        }
        // 1.2.玩家如果已经存在则直接返回错误信息
        PlayerRole playerRoleVo = dbVerifyUtils.isHasRole(sc.getGameId(),sc.getPlayerId(),sc.getRoleId());
        if(playerRoleVo != null){
            throw new AlreadyExistsPlayerRoleException();
        }
        // 2.创建玩家角色信息
        PlayerRole role = createPlayerRole(sc, roleName);

        if(role == null || role.getRoleId() == null){
            throw new CreateRoleException();
        }
        // 缓存
        playerRoleVo = playerRoleCache.get(sc.getRoleId(), sc.getGameId());
        // 埋点(注释)（1）
        // logService.setRoleBuildLog(playerRoleVO);
        return playerRoleVo;
    }

    /**
     * 创建游戏角色第二步
     * @param roleName
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public PlayerRole createPlayerRole(ServiceContext sc, String roleName){
        Date now = new Date();
        System.out.println("role==============================>playerId+"
                +sc.getPlayerId()+";gameid+"+sc.getGameId()+";rolename+"+roleName+";roleAvatar");
        PlayerRole role = new PlayerRole();

        role.setPlayerId(sc.getPlayerId());
        role.setGameId(sc.getGameId());
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

        // 缓存
        AccountVO account = redisService.getByUserId(sc.getPlayerId(), Integer.valueOf(sc.getGameId()));
        System.out.println("account======================[]][][][][]["+account);
        if(account != null) {
            role.setChannelId(account.getChannelId());
            System.out.println("pf==========================[][][][][][]"+role.getChannelId());
        }

        playerRoleMapper.insert(role);

        return role;
    }

    /**
     * 更新指定玩家的最后登录时间
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public void updateLastLoginTime(ServiceContext sc){
        PlayerRole record = new PlayerRole();
        record.setRoleId(sc.getRoleId());
        record.setGameId(sc.getGameId());
        record.setLastLoginTime(Calendar.getInstance().getTime());
        // 上传最后一次登录的服务器ID（修改，待验证）
        if("".equals(ServerID)){
            ServerID = GetBeanHelper.getServerConfig().getProperty(AppConfig.SERVER_ID, "");
            System.out.println("===================[][][][****][ServerId][]"+ServerID);
        }
        record.setLastLoginServer(StringUtils.getInt(ServerID));
        playerRoleMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 验证玩家角色名是否重复
     * @param admiralName
     * @throws AdmiralNameIsNotNullableException
     * @throws AdmiralNameIsTooLongException
     * @throws AdmiralNameIncludeHarmonyException
     * @throws AdmiralNameCoincideException
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public void checkNewPlayerName(ServiceContext sc, String admiralName) throws AdmiralNameIsNotNullableException, AdmiralNameIsTooLongException, AdmiralNameIncludeHarmonyException, AdmiralNameCoincideException {
        // 1.1.舰队长不允许重名
        shieldingWordsUtil.checkAdmiralName(admiralName);

        // 1.2.舰队长不允许重名
        Long result = this.findByAdmiralName(admiralName,sc.getGameId());
        if(result != null)
            throw new AdmiralNameCoincideException();
    }

    /**
     * 获取指定角色名的roleId,如果没有返回null
     * @param roleName
     * @return
     */
    public Long findByAdmiralName(String roleName,String gameId) {
        List<PlayerRole> playerRoleList = playerRoleMapper.selectByRoleName(roleName,gameId);

        if(playerRoleList.size() != 1){
            return null;
        }

        return playerRoleList.get(0).getRoleId();
    }
}
