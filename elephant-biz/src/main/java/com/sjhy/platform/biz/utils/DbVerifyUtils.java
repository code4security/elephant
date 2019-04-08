package com.sjhy.platform.biz.utils;

import com.sjhy.platform.biz.bo.RoleBO;
import com.sjhy.platform.biz.redis.RedisUtil;
import com.sjhy.platform.biz.redis.redisVo.redisCont.KrGlobalCache;
import com.sjhy.platform.client.dto.game.ChannelAndVersion;
import com.sjhy.platform.client.dto.game.Game;
import com.sjhy.platform.client.dto.game.GameNotify;
import com.sjhy.platform.client.dto.player.PlayerGameOss;
import com.sjhy.platform.client.dto.player.PlayerIos;
import com.sjhy.platform.client.dto.player.PlayerRole;
import com.sjhy.platform.client.service.DbVerify;
import com.sjhy.platform.persist.mysql.game.ChannelAndVersionMapper;
import com.sjhy.platform.persist.mysql.game.GameMapper;
import com.sjhy.platform.persist.mysql.player.PlayerGameOssMapper;
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
    @Resource
    private KrGlobalCache krGlobalCache;
    @Autowired
    private PlayerGameOssMapper playerGameOssMapper;

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
            if (game != null && game.getType() ==1){
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
     * 验证ios玩家id是否存在  存在 返回true
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
        try {
            PlayerRole roleVO = new PlayerRole();
            if (playerId != null && roleId ==null && redis.get(gameId+"r_"+playerId)!= null){
                roleId = Long.parseLong(redis.get(gameId+"r_"+playerId).toString());
            }

            roleVO = (PlayerRole) redis.get(roleId+"_r_"+gameId);
            if (roleVO != null){
                return roleVO;
            }

            if (roleId != null){
                roleVO = playerRoleMapper.selectByRoleId(gameId, roleId);
                if(roleVO == null){
                    return null;
                }
                redis.set(roleVO.getRoleId()+"_r_"+gameId,roleVO,3600);
                return roleVO;
            }else if (playerId != null){
                roleVO = playerRoleMapper.selectByPlayerId(gameId, playerId);
                if(roleVO ==null){
                    return null;
                }
                redis.set(roleVO.getRoleId()+"_r_"+gameId,roleVO,3600);
                redis.set(gameId+"r_"+playerId,roleVO.getRoleId(),300);
                return roleVO;
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

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
    /**
     * 获取公告
     */
    public GameNotify isHasNotity(String gameId) {
        GameNotify gameNotify = krGlobalCache.getGsNotify(Integer.valueOf(gameId));
        return gameNotify;
    }

    /**
     * 获取玩家最后登陆服务器
     * @param gameId
     * @param roleId
     * @return
     */
    public Integer isHasLastServer(String gameId,Long roleId){
        int status = 0;
        Integer serverId = (Integer) redis.get(roleId+"_serverId"+gameId);
        if (serverId != null){
            status = serverId;
        }else {
            serverId = playerRoleMapper.selectByRoleId(gameId,roleId).getLastLoginServer();
            if (serverId == null){
                status = -1;
            }
            redis.set(roleId+"_serverId"+gameId,serverId,600);
        }
        if (status != -1 && !RoleBO.ServerID.equals(status+"")){
            return -2;
        }
        return 0;
    }

    /**
     * 获取oss存档,返回list
     * @param gameId
     * @param roleId
     * @return
     */
    public List<PlayerGameOss> isHasOss(String gameId, Long roleId){
        List<PlayerGameOss> playerGameOss = (List<PlayerGameOss>) redis.get(gameId+"_oss_"+roleId);
        if (playerGameOss !=null){
            return playerGameOss;
        }
        playerGameOss = playerGameOssMapper.selectByRoleId(gameId,roleId);
        if (playerGameOss == null){
            return null;
        }
        redis.set(gameId+"_oss_"+roleId,playerGameOss,30);
        return playerGameOss;
    }

    /**
     * 获取oss存档,返回对象
     * @param gameId
     * @param roleId
     * @return
     */
    public PlayerGameOss isHasOssGame(String gameId, Long roleId){
        PlayerGameOss playerGameOss = (PlayerGameOss) redis.get(gameId+"_ossGame_"+roleId);
        if (playerGameOss !=null){
            return playerGameOss;
        }
        playerGameOss = playerGameOssMapper.selectByOssRoleGame(gameId,roleId);
        if (playerGameOss == null){
            return null;
        }
        redis.set(gameId+"_ossGame_"+roleId,playerGameOss,30);
        return playerGameOss;
    }

    /**
     * 根据id和gameid查询存档
     * @param id
     * @param gameId
     * @return
     */
    public PlayerGameOss isHasOssGameKey(Integer id,String gameId){
        PlayerGameOss playerGameOss = (PlayerGameOss) redis.get(id+"_ossId_"+gameId);
        if (playerGameOss !=null){
            return playerGameOss;
        }
        playerGameOss = playerGameOssMapper.selectByGameKey(id,gameId);
        if (playerGameOss == null){
            return null;
        }
        redis.set(id+"_ossId_"+gameId,playerGameOss,300);
        return playerGameOss;
    }

    /**
     * 根据objkey查询存档
     * @param ossObj
     * @return
     */
    public PlayerGameOss isHasOssObjKey(PlayerGameOss ossObj){
        PlayerGameOss playerGameOss = (PlayerGameOss) redis.get("ossObj_"+ossObj.getObjKey());
        if (playerGameOss !=null){
            return playerGameOss;
        }
        playerGameOss = playerGameOssMapper.selectByRoleIdAndObjKey(ossObj);
        if (playerGameOss == null){
            return null;
        }
        redis.set("ossObj_"+ossObj.getObjKey(),playerGameOss,300);
        return playerGameOss;
    }

    public PlayerGameOss ossSelectByPrimaryKey(Integer id){
        return playerGameOssMapper.selectByPrimaryKey(id);
    }
    public void ossUpdateByPrimaryKeySelective(PlayerGameOss playerGameOss){
        playerGameOssMapper.updateByPrimaryKeySelective(playerGameOss);
    }
    public void ossUpdateEndtimeByRoleId(String gameId,Long roleId){
        playerGameOssMapper.updateEndtimeByRoleId(gameId,roleId);
    }

}
