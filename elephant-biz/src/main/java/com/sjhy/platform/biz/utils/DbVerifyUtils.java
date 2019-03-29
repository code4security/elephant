package com.sjhy.platform.biz.utils;

import com.sjhy.platform.biz.redis.RedisUtil;
import com.sjhy.platform.biz.redis.redisVo.redisCont.KrGlobalCache;
import com.sjhy.platform.client.dto.game.ChannelAndVersion;
import com.sjhy.platform.client.dto.game.Game;
import com.sjhy.platform.client.dto.player.PlayerIos;
import com.sjhy.platform.client.dto.player.PlayerRole;
import com.sjhy.platform.client.dto.vo.BulletinLoginVO;
import com.sjhy.platform.client.service.DbVerify;
import com.sjhy.platform.persist.mysql.game.ChannelAndVersionMapper;
import com.sjhy.platform.persist.mysql.game.GameMapper;
import com.sjhy.platform.persist.mysql.game.GameNotifyMapper;
import com.sjhy.platform.persist.mysql.player.PlayerIosMapper;
import com.sjhy.platform.persist.mysql.player.PlayerRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DbVerifyUtils implements DbVerify {

    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private ChannelAndVersionMapper channelAndVersionMapper;
    @Autowired
    private RedisUtil redis;
    @Autowired
    private PlayerIosMapper playerIosMapper;
    @Autowired
    private PlayerRoleMapper playerRoleMapper;
    @Autowired
    private GameNotifyMapper gameNotifyMapper;
    @Resource
    private KrGlobalCache krGlobalCache;

    /**
     * 验证gameId是否存在数据库
     * @param gameId
     * @return
     */
    public boolean isHasGame(String gameId){
        try {
            Game game = (Game) redis.get("g_"+gameId);
            if (game != null){
                return true;
            }
            game = gameMapper.selectByGameId(gameId);
            if (game != null){
                redis.set("g_"+gameId,game,30);
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            System.out.println("==================[][][game][][]");
            return false;
        }

    }

    /**
     * 验证渠道id是否存在
     * @param channelId
     * @param gameId
     * @return
     */
    public boolean isHasChannel(String channelId,String gameId){
        try {
            ChannelAndVersion channelAndVersion = (ChannelAndVersion) redis.get("c_"+channelId);
            if (channelAndVersion != null){
                return true;
            }
            channelAndVersion = channelAndVersionMapper.verifyChannel
                            (new ChannelAndVersion(null,channelId,gameId,null,null,null,null));
            if (channelAndVersion != null){
                redis.set("c_"+channelId,channelAndVersion,30);
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            System.out.println("==================[][][channel][][]");
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 验证ios玩家id是否存在
     * @param iosId
     * @param gameId
     * @param channelId
     * @return
     */
    public boolean isHasIos(Long iosId, String gameId, String channelId) {
        try {
            PlayerIos playerIos = playerIosMapper.selectByGameId
                    (new PlayerIos(iosId,gameId,channelId,null,null,null,null,null,null));
            if (playerIos != null){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 验证角色是否存在
     * @param gameId
     * @param playerId
     * @param roleId
     * @return
     */
    public PlayerRole isHasRole(String gameId,Long playerId,Long roleId){
        PlayerRole roleVO = new PlayerRole();
        if (playerId != null && roleId ==null && redis.get("r_"+playerId)!= null){
            roleId = Long.parseLong(redis.get("r_"+playerId).toString());
        }

        roleVO = (PlayerRole) redis.get(roleId+"_r_"+gameId);
        if (roleVO != null){
            return roleVO;
        }

        if (roleId != null){
            roleVO = playerRoleMapper.selectByRoleId(gameId, roleId);
            redis.set(roleVO.getRoleId()+"_r_"+gameId,roleVO,3600);
            return roleVO;
        }else if (playerId != null){
            roleVO = playerRoleMapper.selectByPlayerId(gameId, playerId);
            redis.set(roleVO.getRoleId()+"_r_"+gameId,roleVO,3600);
            redis.set("r_"+playerId,roleVO.getRoleId(),300);
            return roleVO;
        }
        return null;
    }

    /**
     * 删除角色
     * @param gameId
     * @param roleId
     */
    public void remoteRole(String gameId,Long roleId){
        redis.del(roleId+"_r_"+gameId);
    }

    @Override
    public List<BulletinLoginVO> isHasNotity(String gameId) {
        List<BulletinLoginVO> notity = (List<BulletinLoginVO>) krGlobalCache.getGsNotify(Integer.valueOf(gameId));
        return notity;
    }

}
