package com.sjhy.platform.biz.deploy.cache.redis;

import com.sjhy.platform.biz.deploy.cache.cachevo.GameNotifyVO;
import com.sjhy.platform.biz.deploy.cache.cachevo.GameVO;
import com.sjhy.platform.biz.deploy.cache.cachevo.ServerVO;
import com.sjhy.platform.biz.deploy.cache.cachevo.VirtualCurrencyVO;
import com.sjhy.platform.biz.deploy.utils.GetBeanHelper;
import com.sjhy.platform.client.dto.fixed.VirtualCurrency;
import com.sjhy.platform.client.dto.game.Game;
import com.sjhy.platform.client.dto.game.GameNotify;
import com.sjhy.platform.client.dto.game.Server;
import com.sjhy.platform.persist.mysql.fixed.VirtualCurrencyMapper;
import com.sjhy.platform.persist.mysql.game.GameMapper;
import com.sjhy.platform.persist.mysql.game.GameNotifyMapper;
import com.sjhy.platform.persist.mysql.game.ServerMapper;
import org.springframework.beans.BeanUtils;

import java.util.Calendar;

/**
 * 相当于DAO层,不变的字典表由 Mybitas 缓存,变化的表手工缓存
 * <p>类说明:玩家物品信息的缓存类(仅限于背包中和装备栏中)。key-roleId，value-PlayerRoleVO</p>
 * <p>文件名： PlayerRoleVOCacheManager.java</p>
 * <p>创建人及时间：	</p>
 *
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改描述：</p>
 *
 */
public class KrGlobalCache {
	private static final String REGION = "kairo_global";
	
	public static ServerVO getServerById(int serverId){
		CacheObject cache = KrCache.getChannel().get(REGION, serverId);
		//System.out.println("thisddddddddddd====>"+serverId);
		if(cache == null || cache.getValue() == null){
			ServerMapper serverMapper = GetBeanHelper.getBean(ServerMapper.class);
			
			Server server = serverMapper.selectByPrimaryKey(serverId);
			//System.out.println("ddddddddddddddddd====>"+serverId);
			if(server != null){
				ServerVO vo = new ServerVO();
				//System.out.println("dddddddddddddddddeee====>"+server.getName());	
				BeanUtils.copyProperties(server, vo);
				
				KrCache.getChannel().set(REGION, serverId, vo);
				
				return vo;
			}
			
			return null;
		}else{
			//System.out.println("dddddddddddddddddeee====>"+((ServerVO)cache.getValue()).getName());	
			return (ServerVO)cache.getValue();
		}
	}
	
	public static void delServerById(int serverId){
		KrCache.getChannel().evict(REGION, serverId);
	}
	
	public static GameVO getGameById(int gameId){
		CacheObject cache = KrCache.getChannel().get(REGION, gameId);
		
		if(cache == null || cache.getValue() == null){
			GameMapper gameDicMapper = GetBeanHelper.getBean(GameMapper.class);
			
			Game game = gameDicMapper.selectByPrimaryKey(gameId);
			
			if(game != null){
				GameVO vo = new GameVO();
				
				BeanUtils.copyProperties(game, vo);
				
				KrCache.getChannel().set(REGION, gameId, vo);
				
				return vo;
			}
			
			return null;
		}else{
			return (GameVO)cache.getValue();
		}
	}
	
	public static void delGameById(int gameId){
		KrCache.getChannel().evict(REGION, gameId);
	}
	
	public static GameNotify getGsNotify(int gameId){
		CacheObject cache = KrCache.getChannel().get(REGION, gameId+"_notify");
		
		if(cache == null || cache.getValue() == null){
			GameNotify gameNotify = GetBeanHelper.getBean(GameNotifyMapper.class).selectLatestNotify(String.valueOf(gameId));
			
			if(gameNotify == null){
				return null;
			}
			
			long now = Calendar.getInstance().getTime().getTime();
			if(now > gameNotify.getBeginTime().getTime() && now < gameNotify.getEndTime().getTime()){
				GameNotifyVO vo = new GameNotifyVO();
				
				BeanUtils.copyProperties(gameNotify, vo);
				
				putGsNotify(Integer.parseInt(vo.getGameId()), vo);
				
				return vo;
			}
			
			return null;
		}else{
			long now = Calendar.getInstance().getTime().getTime();
			
			GameNotify gameNotify = (GameNotify)cache.getValue();
			if(now > gameNotify.getBeginTime().getTime() && now < gameNotify.getEndTime().getTime()){
				return gameNotify;
			}else{
				delGsNotify(Integer.parseInt(gameNotify.getGameId()));
			}
			
			return null;
		}
	}
	
	public static void putGsNotify(int gameId, GameNotifyVO gameNotify){
		KrCache.getChannel().set(REGION, gameId+"_notify", gameNotify);
	}
	
	public static void delGsNotify(int gameId){
		KrCache.getChannel().evict(REGION, gameId+"_notify");
	}
	
	public static VirtualCurrencyVO getVirtualCurrency(int virtualCurrencyId) {
		CacheObject cache = KrCache.getChannel().get(REGION, virtualCurrencyId+"_virtualCurrency");
		
		if(cache == null || cache.getValue() == null){
			VirtualCurrency virtualCurrency = GetBeanHelper.getBean(VirtualCurrencyMapper.class).selectByPrimaryKey(virtualCurrencyId);
			
			if(virtualCurrency == null) {
				return null;
			}

			VirtualCurrencyVO vo = new VirtualCurrencyVO();
			
			BeanUtils.copyProperties(virtualCurrency, vo);
			
			putItem(virtualCurrencyId, vo);
			
			return vo;
		}
		
		return (VirtualCurrencyVO)cache.getValue();
	}
	
	public static void putItem(int virtualCurrencyId, VirtualCurrencyVO virtualCurrency){
		KrCache.getChannel().set(REGION, virtualCurrencyId+"_virtualCurrency", virtualCurrency);
	}
}
