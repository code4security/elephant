package com.sjhy.platform.biz.deploy.redis;

import com.sjhy.platform.client.dto.vo.AccountVO;
import com.sjhy.platform.client.dto.vo.LoginSessionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RedisServiceImpl extends RedisService {

    private Map<String, String> userToChUser = new ConcurrentHashMap<String, String>();//  {userId_gameId, chUserId_gameId}

    public void put( int key, LoginSessionVO value )
    {
        super.set( String.valueOf(key), value );
    }

    public void put(String key, int gameId, AccountVO value)
    {
        value.setGameId(gameId);
        super.set(key+"_"+gameId, value);

        if(value.getPlayerId() > 0){
            userToChUser.put(value.getPlayerId() + "_" + value.getGameId(), key + "_" + value.getGameId());
        }
    }

    public void update(String chUserId, long playerId, int gameId){
        AccountVO account = (AccountVO) get(chUserId + "_" + gameId);
        if(account != null) {
            account.setPlayerId(playerId);
        }
        put(chUserId, gameId, account);
    }
}
