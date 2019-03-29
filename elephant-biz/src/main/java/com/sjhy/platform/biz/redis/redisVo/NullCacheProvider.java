package com.sjhy.platform.biz.redis.redisVo;

import com.sjhy.platform.biz.redis.CacheExpiredListener;
import com.sjhy.platform.biz.redis.CacheProvider;

import java.util.Properties;

/**
 * @author winterlau
 */
public class NullCacheProvider implements CacheProvider {

	private final static NullCache cache = new NullCache();

	@Override
	public String name() {
		return "none";
	}

	/* (non-Javadoc)
	 * @see net.oschina.j2cache.CacheProvider#buildCache(java.lang.String, boolean, net.oschina.j2cache.CacheExpiredListener)
	 */
	@Override
	public ICache buildCache(String regionName, boolean autoCreate, CacheExpiredListener listener) {
		return cache;
	}

	/* (non-Javadoc)
	 * @see net.oschina.j2cache.CacheProvider#start()
	 */
	@Override
	public void start(Properties props) {
	}

	/* (non-Javadoc)
	 * @see net.oschina.j2cache.CacheProvider#stop()
	 */
	@Override
	public void stop() {
	}

}
