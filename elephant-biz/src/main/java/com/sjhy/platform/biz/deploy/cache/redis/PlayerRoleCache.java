/*
package com.sjhy.platform.biz.deploy.cache.redis;

import com.sjhy.platform.biz.deploy.utils.GetBeanHelper;
import com.sjhy.platform.client.dto.vo.PlayerRoleVO;
import org.springframework.beans.BeanUtils;

*/
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
 *//*

public class PlayerRoleCache {
	private static final String REGION = "player_role";
	
	*/
/**
	 * 主键为:roleId , 如果不存在,返回空
	 * 
	 *//*

	public static PlayerRoleVO get(Long key) {
		CacheObject value = KrCache.getChannel().get(REGION, key);
		
		if (value != null && value.getValue() != null){
			return (PlayerRoleVO)value.getValue();
		}

		// 玩家基本信息
		PlayerRoleService playerRoleService = GetBeanHelper.getBean(PlayerRoleService.class);
		
		//System.out.println("aa->"+Calendar.getInstance().getTimeInMillis());
		PlayerRole playerRole = playerRoleService.getDbByPrimaryKey(key);
		if(playerRole == null){
			return null;
		}
		
		PlayerRoleVO role = new PlayerRoleVO();
		
		// 属性copy
		BeanUtils.copyProperties(playerRole, role);
		//System.out.println("bb->"+Calendar.getInstance().getTimeInMillis());
		KrCache.getChannel().set(REGION, role.getRoleId(), role);
		
		KrCache.getChannel().set(REGION, role.getRoleId()+"_server", role.getLastLoginServer());
		
		KrCache.getChannel().set(REGION, role.getPlayerId() +"_"+ role.getGameId(), role.getRoleId());
		
		return role;
	}

	*/
/**
	 * 所有服务器要求一致同步，所以只从redis中获取
	 * @param roleId
	 * @return
	 *//*

	public static int getLastLoginServerId(long roleId){
		CacheObject value = KrCache.getChannel().getFromRedis(REGION, roleId+"_server");
		
		if (value != null && value.getValue() != null){
			return (int)value.getValue();
		}else{
			PlayerRoleService playerRoleService = GetBeanHelper.getBean(PlayerRoleService.class);
			
			PlayerRole playerRole = playerRoleService.getDbByPrimaryKey(roleId);
			if(playerRole == null){
				return -1;
			}
			
			KrCache.getChannel().set(REGION, roleId+"_server", playerRole.getLastLoginServer());
			
			return playerRole.getLastLoginServer();
		}
	}
	
	public static PlayerRoleVO get(long userId, int gameId) {
		CacheObject value = KrCache.getChannel().get(REGION, userId+"_"+gameId);
		
		if (value != null && value.getValue() != null){
			return get((long)value.getValue());
		}
		
		// 玩家基本信息
		PlayerRoleService playerRoleService = GetBeanHelper.getBean(PlayerRoleService.class);
				
		Long roleId = playerRoleService.getRoleIdByPrimaryKey(userId, gameId);
		
		if(roleId == null){
			return null;
		}
		
		return get(roleId);
	}
	
	public static void remove(long roleId){
		PlayerRoleVO role = get(roleId);
		
		if(role == null){
			return;
		}
		
		KrCache.getChannel().evict(REGION, roleId);
		KrCache.getChannel().evict(REGION, role.getPlayerId()+"_"+role.getGameId());
		
		KrCache.getChannel().evict(REGION, roleId+"_server");
	}
	
	public static void update(PlayerRoleVO role) {
		if(role == null) {
			return;
		}
		
		KrCache.getChannel().set(REGION, role.getRoleId(), role);
	}
	
	public static void remove(long userId, int gameId){
		PlayerRoleVO role = get(userId, gameId);
		
		if(role == null){
			return;
		}
		
		KrCache.getChannel().evict(REGION, role.getRoleId());
		KrCache.getChannel().evict(REGION, role.getPlayerId()+"_"+role.getGameId());
	}
}
*/
