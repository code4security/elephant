package com.sjhy.platform.biz.deploy.cache.redis;

import com.sjhy.platform.biz.deploy.exception.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Redis 缓存基于Hashs实现
 *
 * @author wendal
 */
public class RedisCache implements ICache {


    private String locksPattern = "%s:lock:";
    private String locksNamePattern = locksPattern + "*";
    private String lockPattern = locksPattern + "%s"; //暂时使用keys xxx:* 因为一般也就最多1024个key不会阻塞 后续可以改造成scan
    private static byte[] NX = "NX".getBytes();// NX -- Only set the key if it does not already exist.
    @SuppressWarnings("unused")
	private static byte[] XX = "XX".getBytes();//XX -- Only set the key if it already exist.

    @SuppressWarnings("unused")
	private static byte[] EX = "EX".getBytes();//expire time units: EX = seconds;

    private static byte[] PX = "PX".getBytes();//expire time units:  PX = milliseconds

    private static final String REDIS_NAMESPACE = "KAIROGAME";

    private final static Logger log = LoggerFactory.getLogger(RedisCache.class);

    // 记录region
    protected byte[] region2;
    protected String region;
    protected RedisCacheProxy redisCacheProxy;

    public RedisCache(String region, RedisCacheProxy redisCacheProxy) {
        if (region == null || region.isEmpty())
            region = "_"; // 缺省region

        region = getRegionName(region);
        this.redisCacheProxy = redisCacheProxy;
        this.region = region;
        this.region2 = region.getBytes();
    }

    /**
     * 在region里增加一个可选的层级,作为命名空间,使结构更加清晰
     * 同时满足小型应用,多个J2Cache共享一个redis database的场景
     *
     * @param region
     * @return
     */
    private String getRegionName(String region) {
        String nameSpace = REDIS_NAMESPACE;
        
        if (nameSpace != null && !nameSpace.isEmpty()) {
            region = nameSpace + ":" + region;
        }
        return region;
    }

    protected byte[] getKeyName(Object key) {
        if (key instanceof Number)
            return ("I:" + key).getBytes();
        else if (key instanceof String || key instanceof StringBuilder || key instanceof StringBuffer)
            return ("S:" + key).getBytes();
        return ("O:" + key).getBytes();
    }

    public Object get(Object key) throws CacheException {
        if (null == key)
            return null;
        Object obj = null;
        try {
            byte[] keyName = getKeyName(key);
            byte[] b = redisCacheProxy.hget(region2, keyName);
            if (b != null) {
                obj = SerializationUtils.deserialize(b);
            } else if (redisCacheProxy.isBlock()) {
                byte[] lockKey = getLockKey(key);
                boolean locked = getLock(lockKey);
                if (locked || canReentrant(key)) {
                    return null;
                } else {
                    int timeLeft = redisCacheProxy.getTimeOutMillis();
                    while (timeLeft > 0) {
                        Thread.sleep(redisCacheProxy.getTimeWaitMillis());
                        timeLeft -= redisCacheProxy.getTimeWaitMillis();
                        b = redisCacheProxy.hget(region2, keyName);
                        if (b != null) {
                            obj = SerializationUtils.deserialize(b);
                            break;
                        } else {
                            //如果拿不到再尝试一次获取lock，防止出现部分情况一直没有put导致等待时间过长。后续要改造成可重入
                            if (getLock(lockKey)) {
                                return null;
                            }
                        }
                        //超时是应该抛异常呢还是直接返回null？ 目前返回null
                    }
                }

            }
        } catch (Exception e) {
            log.error("Error occured when get data from redis2 cache", e);
            if (e instanceof IOException || e instanceof NullPointerException)
                evict(key);
        }
        return obj;
    }

    private boolean canReentrant(Object key) {
        //对于缓存来说要求不精确，使用线程id即可
        try {
            String value = redisCacheProxy.get(getLockKeyString(key));
            if (value != null) {
                long oriThreadId = Long.parseLong(value);
                return oriThreadId == Thread.currentThread().getId();
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    private boolean getLock(byte[] lockKey) {
        return getLock(lockKey, String.valueOf(Thread.currentThread().getId()).getBytes());
    }

    private boolean getLock(byte[] lockKey, byte[] keyName) {
        return "OK".equals(redisCacheProxy.set(lockKey, keyName, NX, PX, redisCacheProxy.getTimeLockMillis()));
    }

    private void releaseLock(byte[] lockKey) {
        redisCacheProxy.del(lockKey);
    }

    private String getLockKeyString(Object key) {
        return String.format(lockPattern, region, key.hashCode() % redisCacheProxy.getStripes());
    }

    private byte[] getLockKey(Object key) {
        String keyName = getLockKeyString(key);
        return keyName.getBytes();
    }

    public void put(Object key, Object value) throws CacheException {
        if (key == null)
            return;
        if (value == null)
            evict(key);
        else {
            try {
                redisCacheProxy.hset(region2, getKeyName(key), SerializationUtils.serialize(value));
                if (redisCacheProxy.isBlock()) {
                    releaseLock(getLockKey(key));
                }
            } catch (Exception e) {
                throw new CacheException(e);
            }
        }
    }

    public void update(Object key, Object value) throws CacheException {
        put(key, value);
    }

    public void evict(Object key) throws CacheException {
        if (key == null)
            return;
        try {
            redisCacheProxy.hdel(region2, getKeyName(key));
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    public void evict(List keys) throws CacheException {
        if (keys == null || keys.size() == 0)
            return;
        try {
            int size = keys.size();
            byte[][] okeys = new byte[size][];
            for (int i = 0; i < size; i++) {
                okeys[i] = getKeyName(keys.get(i));
            }
            redisCacheProxy.hdel(region2, okeys);
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    public List<String> keys() throws CacheException {
        try {
            return new ArrayList<>(redisCacheProxy.hkeys(region));
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    public void clear() throws CacheException {
        try {
            redisCacheProxy.del(region2);
            if (redisCacheProxy.isBlock()) {
                Set<byte[]> keys = redisCacheProxy.keys(String.format(locksNamePattern, region).getBytes());
                if (!keys.isEmpty()) {
                    redisCacheProxy.del(keys.toArray(new byte[][]{}));
                }
            }
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    public void destroy() throws CacheException {
        this.clear();
    }

    @Override
    public void put(Object key, Object value, Integer expireInSec) throws CacheException {
        if (key == null)
            return;
        if (value == null)
            evict(key);
        else {
            try {
                redisCacheProxy.hset(region2, getKeyName(key), SerializationUtils.serialize(value), expireInSec);
                if (redisCacheProxy.isBlock()) {
                    releaseLock(getLockKey(key));
                }
            } catch (Exception e) {
                throw new CacheException(e);
            }
        }
    }

    @Override
    public void update(Object key, Object value, Integer expireInSec) throws CacheException {
        put(key, value, expireInSec);
    }
}
