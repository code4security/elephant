package com.sjhy.platform.biz.redis.redisVo.redisCont;

import com.sjhy.platform.biz.redis.redisVo.CacheObject;
import com.sjhy.platform.biz.redis.redisVo.KrCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SessionKey 专用库,不论用登陆哪个游戏,都使用同一个 SessionKey 库
 * 1、HDEL：删除指定key的hash表的值
 * 2、HEXISTS：判断指定的key在hash表中是否存在
 * 3、HGET：从hash表中获取指定key的值
 * 4、HGETALL：获取hash中所有的元素,包括key和value
 * 5、HINCRBY：增加hash表中指定key的value的值,(可加可减),value会变的
 * 6、HKEYS：返回hash表中所有的Key
 * 7、HLEN：返回hash表中的记录数
 * 8、HMGET：一次获取hash中的多个值
 * 9、HMSET：一次设定hash表中的多个key-value对
 * 10、HSET：设定hash表中指定的key-valve对
 * 11、HSETNX：设定hash表中指定的key-valve对,当且仅当key不存在时生效
 * 12、HVALS：获取hash表中所有的value
 * @author SHACS
 *
 */
public class SessionKeyHMapCpt {
	private static final Logger logger = LoggerFactory.getLogger(SessionKeyHMapCpt.class);

	private static final String REGION = "sessionkey";

	/**
	 * 从库中获取 SessionKey 
	 * @param uid 玩家id
	 * @return 如果未登录,则返回null
	 */
	public static String getSessionKey(String uid) {
		CacheObject obj = KrCache.getChannel().get(REGION, uid);
		
		logger.debug("SessionKeyHMapCpt|method=getSessionKey|uid="+uid);
		
		if(obj == null || obj.getValue() == null){
			return null;
		}else{
			return obj.getValue().toString();
		}
    }
	
	/**
	 * 保存数据到库中
	 * @param uid
	 * @param sessionKey
	 */
	public static void saveOrUpdate(String uid , String sessionKey) {
		logger.debug("SessionKeyHMapCpt|method=saveOrUpdate|uid="+uid+"|sessionkey=" + sessionKey);
		
		KrCache.getChannel().set(REGION, uid, sessionKey, 3600);
    }
	
	public static void remove(String uid){
		logger.debug("SessionKeyHMapCpt|method=remove|uid="+uid);
		
		KrCache.getChannel().evict(REGION, uid);
	}
}




























