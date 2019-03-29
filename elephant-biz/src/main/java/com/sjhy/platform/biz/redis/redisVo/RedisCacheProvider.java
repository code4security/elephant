package com.sjhy.platform.biz.redis.redisVo;

import com.sjhy.platform.biz.redis.CacheExpiredListener;
import com.sjhy.platform.biz.redis.CacheProvider;
import com.sjhy.platform.biz.redis.redisVo.support.BlockSupportConfig;
import com.sjhy.platform.biz.redis.redisVo.support.RedisClientFactoryAdapter;
import com.sjhy.platform.biz.redis.redisVo.support.RedisPoolConfig;
import com.sjhy.platform.client.deploy.exception.CacheException;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Redis 缓存实现
 *
 * @author Winter Lau
 * @author wendal
 */
public class RedisCacheProvider implements CacheProvider {

    private static RedisCacheProxy redisCacheProxy;

    protected ConcurrentHashMap<String, RedisCache> caches = new ConcurrentHashMap<>();

    public String name() {
        return "redis";
    }

    // 这个实现有个问题,如果不使用RedisCacheProvider,但又使用RedisCacheChannel,这就NPE了
    public RedisCacheProxy getResource() {
        if (redisCacheProxy == null) {

        }

        return redisCacheProxy;
    }

    @Override
    public ICache buildCache(String regionName, boolean autoCreate, CacheExpiredListener listener) throws CacheException {
        // 虽然这个实现在并发时有概率出现同一各regionName返回不同的实例
        // 但返回的实例一次性使用,所以加锁了并没有增加收益
        RedisCache cache = caches.get(regionName);
        if (cache == null) {
            cache = new RedisCache(regionName, redisCacheProxy);
            caches.put(regionName, cache);
        }
        return cache;
    }

    @Override
    public void start(Properties props) throws CacheException {
        RedisPoolConfig config = new RedisPoolConfig();

        config.setHost(getProperty(props, "host", "127.0.0.1"));
        config.setPort(getProperty(props, "port", 6379));
        config.setPassword(props.getProperty("password", null));
        config.setTimeout(getProperty(props, "timeout", 2000));
        config.setBlockWhenExhausted(getProperty(props, "blockWhenExhausted", true));
        config.setMaxIdle(getProperty(props, "maxIdle", 10));
        config.setMinIdle(getProperty(props, "minIdle", 5));
        config.setMaxTotal(getProperty(props, "maxTotal", 10000));
        config.setMaxWaitMillis(getProperty(props, "maxWait", 100));
        config.setTestWhileIdle(getProperty(props, "testWhileIdle", false));
        config.setTestOnBorrow(getProperty(props, "testOnBorrow", true));
        config.setTestOnReturn(getProperty(props, "testOnReturn", false));
        config.setNumTestsPerEvictionRun(getProperty(props, "numTestsPerEvictionRun", 10));
        config.setMinEvictableIdleTimeMillis(getProperty(props, "minEvictableIdleTimeMillis", 1000));
        config.setSoftMinEvictableIdleTimeMillis(getProperty(props, "softMinEvictableIdleTimeMillis", 10));
        config.setTimeBetweenEvictionRunsMillis(getProperty(props, "timeBetweenEvictionRunsMillis", 10));
        config.setLifo(getProperty(props, "lifo", false));

        config.setDatabase(getProperty(props, "database", 0));

        String redisPolicy = getProperty(props, "policy", "single");
        BlockSupportConfig blockSupportConfig = new BlockSupportConfig();
        blockSupportConfig.setBlock(getProperty(props, "block", false));
        blockSupportConfig.setTimeLockMillis(getProperty(props, "timeLockMillis", 60000));
        blockSupportConfig.setTimeWaitMillis(getProperty(props, "timeWaitMillis", 300));
        blockSupportConfig.setTimeOutMillis(getProperty(props, "timeOutMillis", 60000));
        blockSupportConfig.setStripes(getProperty(props, "stripes", 1024));
        
        redisCacheProxy = new RedisCacheProxy(blockSupportConfig, new RedisClientFactoryAdapter(config, RedisClientFactoryAdapter.RedisPolicy.valueOf(redisPolicy)));

    }

    @Override
    public void stop() {
        redisCacheProxy.close();
        caches.clear();
    }

    private static String getProperty(Properties props, String key, String defaultValue) {
        return props.getProperty(key, defaultValue).trim();
    }

    private static int getProperty(Properties props, String key, int defaultValue) {
        try {
            return Integer.parseInt(props.getProperty(key, String.valueOf(defaultValue)).trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private static boolean getProperty(Properties props, String key, boolean defaultValue) {
        return "true".equalsIgnoreCase(props.getProperty(key, String.valueOf(defaultValue)).trim());
    }

}
