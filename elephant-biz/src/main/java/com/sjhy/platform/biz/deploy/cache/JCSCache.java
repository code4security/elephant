package com.sjhy.platform.biz.deploy.cache;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.apache.jcs.engine.behavior.ICompositeCacheAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * 为了使用泛型,而在 JCS.getInstance( regionName ) 外面包了一层应用
 * @author SHACS
 *
 * @param <T>
 */
public class JCSCache<K, V> {
	private static final Logger log = LoggerFactory.getLogger(JCSCache.class);
	private String regionName;
	private JCS cache = null;

	/**
	 * 构造函数
	 * @param regionName
	 */
	public JCSCache(String regionName) {
		try {
			this.regionName = regionName;
			cache = JCS.getInstance(regionName);
        } catch ( CacheException e ) {
            log.error("JCSCache: 构造, regionName="+regionName+" 时,发生异常.", e);
        }
	}
	/**
	 * 返回配置文件基本信息
	 * @return
	 */
	public ICompositeCacheAttributes getCacheAttributes() {
		return cache.getCacheAttributes();
	}
	public String getRegionName() {
		return regionName;
	}
	
	/**
	 * 保存,这里注意 value 不可以为null
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
        try {
            if ( value != null ) {
                cache.put( key, value );                
            }
        } catch ( CacheException e ) {
             log.error( "JCSCache: 保存, regionName="+cache.getCacheAttributes().getCacheName()+" ,key="+key+" ,value="+value+" ,时异常.", e );
        }
	}

	/**
	 * 移除指定对象
	 * @param key
	 */
	public void remove(K key) {
		try {
			cache.remove(key);
		} catch (CacheException e) {
			log.error( "JCSCache: 移除, regionName="+cache.getCacheAttributes().getCacheName()+" ,key="+key+" ,时异常.", e );
		}
	}

	@SuppressWarnings("unchecked")
	public V get(K key) {
		return (V) cache.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public Set<K> getKeySet() {
		return cache.getGroupKeys(regionName);
	}
}
