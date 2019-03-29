package com.sjhy.platform.biz.redis.redisVo.redisCont;

import com.sjhy.platform.biz.redis.redisVo.CacheObject;
import com.sjhy.platform.biz.redis.redisVo.KrCache;
import com.sjhy.platform.client.dto.fixed.VirtualCurrency;
import com.sjhy.platform.client.dto.game.Game;
import com.sjhy.platform.client.dto.game.GameNotify;
import com.sjhy.platform.client.dto.game.Server;
import com.sjhy.platform.client.dto.vo.cachevo.GameNotifyVO;
import com.sjhy.platform.client.dto.vo.cachevo.GameVO;
import com.sjhy.platform.client.dto.vo.cachevo.ItemVO;
import com.sjhy.platform.client.dto.vo.cachevo.ServerVO;
import com.sjhy.platform.persist.mysql.fixed.VirtualCurrencyMapper;
import com.sjhy.platform.persist.mysql.game.GameMapper;
import com.sjhy.platform.persist.mysql.game.GameNotifyMapper;
import com.sjhy.platform.persist.mysql.game.ServerMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
public class KrGlobalCache {
	private static final String REGION = "kairo_global";
	@Autowired
	private GameMapper gameMapper;
	@Autowired
	private ServerMapper serverMapper;
	@Autowired
	private GameNotifyMapper gameNotifyMapper;
	@Autowired
	private VirtualCurrencyMapper virtualCurrencyMapper;

	public ServerVO getServerById(int serverId){
		CacheObject cache = KrCache.getChannel().get(REGION, serverId);
		//System.out.println("thisddddddddddd====>"+serverId);
		if(cache == null || cache.getValue() == null){
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
	
	public void delServerById(int serverId){
		KrCache.getChannel().evict(REGION, serverId);
	}
	
	public GameVO getGameDicById(int gameId){
		CacheObject cache = null;
		try {
			cache = KrCache.getChannel().get(REGION, gameId);
		}catch (Exception e){
			cache = null;
		}

		if(cache == null || cache.getValue() == null){
			Game game = gameMapper.selectByGameId(String.valueOf(gameId));
			
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
	
	public void delGameById(int gameId){
		KrCache.getChannel().evict(REGION, gameId);
	}
	
	public GameNotify getGsNotify(int gameId){
		CacheObject cache = KrCache.getChannel().get(REGION, gameId+"_notify");
		
		if(cache == null || cache.getValue() == null){
			GameNotify gameNotify = gameNotifyMapper.selectLatestNotify(String.valueOf(gameId));
			
			if(gameNotify == null){
				return null;
			}
			
			long now = Calendar.getInstance().getTime().getTime();
			if(now > gameNotify.getCreateTime().getTime() && now < gameNotify.getEndTime().getTime()){
				GameNotifyVO vo = new GameNotifyVO();
				
				BeanUtils.copyProperties(gameNotify, vo);
				
				putGsNotify(vo.getId(), vo);
				
				return vo;
			}
			
			return null;
		}else{
			long now = Calendar.getInstance().getTime().getTime();
			
			GameNotify gameNotify = (GameNotify)cache.getValue();
			if(now > gameNotify.getCreateTime().getTime() && now < gameNotify.getEndTime().getTime()){
				return gameNotify;
			}else{
				delGsNotify(Integer.valueOf(gameNotify.getGameId()));
			}
			
			return null;
		}
	}
	
	public void putGsNotify(int gameId, GameNotifyVO gameNotify){
		KrCache.getChannel().set(REGION, gameId+"_notify", gameNotify);
	}
	
	public void delGsNotify(int gameId){
		KrCache.getChannel().evict(REGION, gameId+"_notify");
	}
	
	public ItemVO getItem(int itemId) {
		CacheObject cache = KrCache.getChannel().get(REGION, itemId+"_item");
		
		if(cache == null || cache.getValue() == null){
			VirtualCurrency item = virtualCurrencyMapper.selectByUnit(String.valueOf(itemId));
			
			if(item == null) {
				return null;
			}
			
			ItemVO vo = new ItemVO();
			
			BeanUtils.copyProperties(item, vo);
			
			putItem(itemId, vo);
			
			return vo;
		}
		
		return (ItemVO)cache.getValue();
	}
	
	public void putItem(int itemId, ItemVO item){
		KrCache.getChannel().set(REGION, itemId+"_item", item);
	}
}
