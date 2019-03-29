package com.sjhy.platform.biz.redis.redisVo;

import com.sjhy.platform.client.deploy.exception.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 缓存入口
 * @author winterlau
 */
public class KrCache {

	private final static Logger log = LoggerFactory.getLogger(KrCache.class);

	private final static String CONFIG_FILE = "/redis.properties";
	private final static RedisCacheChannel channel;
	private final static Properties config;

	static {
		try {
			config = loadConfig();

			String cache_broadcast = config.getProperty("cache.broadcast");

			if ("redis".equalsIgnoreCase(cache_broadcast))
				channel = RedisCacheChannel.getInstance();
			else
				throw new CacheException("Cache Channel not defined. name = " + cache_broadcast);
		} catch (IOException e) {
			throw new CacheException("Unabled to load j2cache configuration " + CONFIG_FILE, e);
		}
	}

	public static RedisCacheChannel getChannel(){
		return channel;
	}

	public static Properties getConfig(){
		return config;
	}

	/**
	 * 加载配置
	 * @return
	 * @throws IOException
	 */
	private static Properties loadConfig() throws IOException {
		log.info("Load J2Cache Config File : [{}].", CONFIG_FILE);
		InputStream configStream = KrCache.class.getClassLoader().getParent().getResourceAsStream(CONFIG_FILE);
		
		if(configStream == null)
			configStream = RedisCacheManager.class.getResourceAsStream(CONFIG_FILE);
		
		if(configStream == null)
			throw new CacheException("Cannot find " + CONFIG_FILE + " !!!");

		Properties props = new Properties();

		try{
			props.load(configStream);
		}finally{
			configStream.close();
		}

		return props;
	}

}
