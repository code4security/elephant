package com.sjhy.platform.biz.redis;

import com.sjhy.platform.client.dto.vo.AccountVO;
import com.sjhy.platform.client.dto.vo.LoginSessionVO;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedisServiceImpl{
    @Resource
    private RedisUtil redis;
    private static final String ROLE = "player_role";
    private static final String GAME = "kairo_global";
    private static final String SESSION = "sessionVo";

    private Map<String, String> userToChUser = new ConcurrentHashMap<String, String>();//  {userId_gameId, chUserId_gameId}

    public void put(String key, int gameId, AccountVO value)
    {
        value.setGameId(gameId);
        redis.set(key+"_"+gameId, value);

        if(value.getPlayerId() > 0){
            userToChUser.put(value.getPlayerId() + "_" + value.getGameId(), key + "_" + value.getGameId());
        }
    }

    public void setSession(int key, LoginSessionVO value){
        redis.hset(SESSION,String.valueOf(key),value);
    }

    public LoginSessionVO getSession(int key){
        return (LoginSessionVO) redis.hget(SESSION,String.valueOf(key));
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

    public AccountVO getByUserId(long user, int gameId){
        String chUser = getChUserId(user, gameId);

        if(chUser != null){
            return (AccountVO) redis.get(chUser);
        }

        return null;
    }
}
