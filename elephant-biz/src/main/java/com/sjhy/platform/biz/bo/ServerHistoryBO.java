package com.sjhy.platform.biz.bo;

import com.sjhy.platform.client.dto.history.ServerHistory;
import com.sjhy.platform.persist.mysql.history.ServerHistoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * 登录过的服务器
 * @author HJ
 *
 */
@Service("ServerHistoryBO")
public class ServerHistoryBO {
	@Resource
    private ServerHistoryMapper serverHistoryMapper;
	
	/**
	 * 玩家登陆,更新最后登陆服务器列表(内部调用)
	 * @param playerId 玩家id
	 * @param serverId 服务器id
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public void updateServerHistory(long playerId, int serverId, String gameId ,String channelId){
		ServerHistory history = new ServerHistory();
		history.setGameId(gameId);
		history.setPlayerId(playerId);
		history.setServerId(serverId);
		ServerHistory serverHistory = serverHistoryMapper.selectByHistory(history);
		
		if(serverHistory == null){ // 该玩家首次登陆新服务器
			serverHistory = new ServerHistory();
			serverHistory.setPlayerId(playerId);
			serverHistory.setGameId(gameId);
			serverHistory.setServerId(serverId);
			serverHistory.setChannelId(channelId);
			serverHistory.setLastLoginTime(Calendar.getInstance().getTime());
			serverHistoryMapper.insert(serverHistory);
		} else { // 更新最后登陆时间
			serverHistory.setLastLoginTime(Calendar.getInstance().getTime());
			serverHistoryMapper.updateByPrimaryKey(serverHistory);
		}
	}

}
