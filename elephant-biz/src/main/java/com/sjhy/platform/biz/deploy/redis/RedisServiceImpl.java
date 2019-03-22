package com.sjhy.platform.biz.deploy.redis;

import com.sjhy.platform.biz.deploy.utils.GetBeanHelper;
import com.sjhy.platform.client.dto.game.Game;
import com.sjhy.platform.client.dto.player.PlayerRole;
import com.sjhy.platform.client.dto.vo.AccountVO;
import com.sjhy.platform.client.dto.vo.cachevo.GameVO;
import com.sjhy.platform.client.dto.vo.cachevo.PlayerRoleVO;
import com.sjhy.platform.persist.mysql.game.GameMapper;
import com.sjhy.platform.persist.mysql.player.PlayerRoleMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedisServiceImpl{
    @Resource
    private RedisUtil redis;
    @Resource
    private PlayerRoleMapper playerRoleMapper;
    private static final String ROLE = "player_role";
    private static final String GAME = "kairo_global";

    private Map<String, String> userToChUser = new ConcurrentHashMap<String, String>();//  {userId_gameId, chUserId_gameId}

    public void put(String key, int gameId, AccountVO value)
    {
        value.setGameId(gameId);
        redis.set(key+"_"+gameId, value);

        if(value.getPlayerId() > 0){
            userToChUser.put(value.getPlayerId() + "_" + value.getGameId(), key + "_" + value.getGameId());
        }
    }

    public void update(String chUserId, long userId, int gameId){
        AccountVO account = (AccountVO) redis.get(chUserId + "_" + gameId);
        if(account != null) {
            account.setPlayerId(userId);
        }
        put(chUserId, gameId, account);
    }

    public String getChUserId(long playeId, int gameId){
        return userToChUser.get(playeId+"_"+gameId);
    }

    public void remove(long roleId,String gameId){
        PlayerRoleVO role = get(roleId,gameId);
        if(role == null){
            return;
        }
        redis.hdel(ROLE, roleId);
        redis.hdel(ROLE, role.getPlayerId()+"_"+role.getGameId());
        redis.hdel(ROLE, roleId+"_server");
    }

    public PlayerRoleVO get(Long key,String gameId) {
        CacheObject value = (CacheObject) redis.hget(ROLE, String.valueOf(key));

        if (value != null && value.getValue() != null){
            return (PlayerRoleVO)value.getValue();
        }

        // 玩家基本信息
        PlayerRole playerRole = playerRoleMapper.selectByPlayerId(gameId,key);
        if(playerRole == null){
            return null;
        }

        PlayerRoleVO role = new PlayerRoleVO();

        // 属性copy
        BeanUtils.copyProperties(playerRole, role);
        //System.out.println("bb->"+Calendar.getInstance().getTimeInMillis());
        redis.hset(ROLE, String.valueOf(role.getRoleId()),role);
        redis.hset(ROLE,role.getRoleId()+"_server",role.getLastLoginServer());
        redis.hset(ROLE,role.getPlayerId()+"_"+role.getGameId(),role.getRoleId());

        return role;
    }

    public GameVO getGameDicById(String gameId){
        CacheObject cache = (CacheObject) redis.hget(GAME, gameId);
        if(cache == null || cache.getValue() == null){
            GameMapper gameMapper = GetBeanHelper.getBean(GameMapper.class);
            Game game = gameMapper.selectByGameId(gameId);
            if(game != null){
                GameVO vo = new GameVO();
                BeanUtils.copyProperties(game, vo);
                redis.hset(GAME, gameId, vo);
                return vo;
            }
            return null;
        }else{
            return (GameVO)cache.getValue();
        }
    }

    public AccountVO getByUserId(long user, int gameId){
        String chUser = getChUserId(user, gameId);

        if(chUser != null){
            return (AccountVO) redis.get(chUser);
        }

        return null;
    }
}
