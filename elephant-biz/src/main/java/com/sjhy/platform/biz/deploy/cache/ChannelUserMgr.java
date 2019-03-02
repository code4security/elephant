package com.sjhy.platform.biz.deploy.cache;

import com.sjhy.platform.client.dto.vo.AccountVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 渠道用户cache
 * @author Administrator
 *
 */
@Component( "ChannelUserMgr" )
public class ChannelUserMgr {
	@Resource
	private JCSCache<String, AccountVO> channelUserCache; // {chUserId_gameId, AccountVO}
	
	private Map<String, String> userToChUser = new ConcurrentHashMap<String, String>();//  {userId_gameId, chUserId_gameId}

	public void put(String key, int gameId, AccountVO value)
	{
		value.setGameId(gameId);
		channelUserCache.put(key+"_"+gameId, value);
		
		if(value.getPlayerId() > 0){
			userToChUser.put(value.getPlayerId() + "_" + value.getGameId(), key + "_" + value.getGameId());
		}
	}

	public AccountVO get(String key)
	{
		return channelUserCache.get(key);
	}

	public void remove(String key)
	{
		AccountVO account = get(key);
		if(account != null){
			userToChUser.remove(account.getPlayerId() + "_" + account.getGameId());
		}
		
		channelUserCache.remove(key);
	}
	
	public void removeByUserId(long user, int gameId){
		String chUser = getChUserId(user, gameId);
		
		if(chUser != null){
			remove(chUser);
		}
	}
	
	public void update(String chUserId, long userId, int gameId){
		AccountVO account = get(chUserId + "_" + gameId);
		if(account != null) {
			account.setPlayerId(userId);
		}
		
		put(chUserId, gameId, account);
	}
	
	public String getChUserId(long user, int gameId){
		return userToChUser.get(user+"_"+gameId);
	}
	
	public AccountVO getByUserId(long user, int gameId){
		String chUser = getChUserId(user, gameId);
		
		if(chUser != null){
			return get(chUser);
		}
		
		return null;
	}
}
