package com.sjhy.platform.biz.redis.redisVo.redisCont;

import com.sjhy.platform.biz.redis.redisVo.CacheObject;
import com.sjhy.platform.biz.redis.redisVo.KrCache;
import com.sjhy.platform.client.dto.player.PlayerRole;
import com.sjhy.platform.client.dto.vo.cachevo.PlayerRoleVO;
import com.sjhy.platform.persist.mysql.player.PlayerRoleMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 相当于DAO层,不变的字典表由 Mybitas 缓存,变化的表手工缓存
 * <p>类说明:玩家物品信息的缓存类(仅限于背包中和装备栏中)。key-roleId，value-PlayerRoleVO</p>
 * <p>文件名： PlayerRoleCache.java</p>
 * <p>创建人及时间：	</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 *
 */
@Component
public class PlayerRoleCache {
	private static final String REGION = "player_role";
	@Autowired
	private PlayerRoleMapper playerRoleMapper;

	/**
	 * 主键为:roleId , 如果不存在,返回空
	 * 
	 */
	public PlayerRoleVO get(Long key, String gameId) {
		CacheObject value = KrCache.getChannel().get(REGION, key);
		
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
		KrCache.getChannel().set(REGION, role.getRoleId()+'_'+role.getGameId(), role);
		
		KrCache.getChannel().set(REGION, role.getRoleId()+"_server"+role.getGameId(), role.getLastLoginServer());
		
		KrCache.getChannel().set(REGION, role.getPlayerId() +"_"+ role.getGameId(), role.getRoleId());
		
		return role;
	}

	/**
	 * 所有服务器要求一致同步，所以只从redis中获取
	 * @param roleId
	 * @return
	 */
	public int getLastLoginServerId(long roleId,String gameId){
		CacheObject value = KrCache.getChannel().getFromRedis(REGION, roleId+"_server"+gameId);
		
		if (value != null && value.getValue() != null){
			return (int)value.getValue();
		}else{
			PlayerRole playerRole = playerRoleMapper.selectByRoleId(gameId, roleId);
			if(playerRole == null){
				return -1;
			}
			
			KrCache.getChannel().set(REGION, roleId+"_server"+gameId, playerRole.getLastLoginServer());
			
			return playerRole.getLastLoginServer();
		}
	}
	
	public PlayerRoleVO get(long userId, int gameId) {
		CacheObject value = KrCache.getChannel().get(REGION, userId+"_"+gameId);
		
		if (value != null && value.getValue() != null){
			return get((long)value.getValue(),String.valueOf(gameId));
		}
		
		// 玩家基本信息
		PlayerRole role = playerRoleMapper.selectByPlayerId(String.valueOf(gameId), userId);
		
		if(role.getRoleId() == null){
			return null;
		}
		
		return get(role.getRoleId(),role.getGameId());
	}
	
	public void remove(long roleId,String gameId){
		PlayerRoleVO role = get(roleId,gameId);
		
		if(role == null){
			return;
		}
		
		KrCache.getChannel().evict(REGION, roleId+"_"+gameId);
		KrCache.getChannel().evict(REGION, role.getPlayerId()+"_"+role.getGameId());
		
		KrCache.getChannel().evict(REGION, roleId+"_server"+gameId);
	}
	
	public void update(PlayerRole role) {
		if(role == null) {
			return;
		}
		
		KrCache.getChannel().set(REGION, role.getRoleId()+'_'+role.getGameId(), role);
	}
	
	public void remove(long userId, int gameId){
		PlayerRoleVO role = get(userId, gameId);
		
		if(role == null){
			return;
		}
		
		KrCache.getChannel().evict(REGION, role.getRoleId());
		KrCache.getChannel().evict(REGION, role.getPlayerId()+"_"+role.getGameId());
	}
}
