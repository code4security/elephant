package com.sjhy.platform.biz.deploy.cache;

import java.util.Set;

public interface ICacheManager<K, V> {
	public V get(K key);
	public V getNotFromDB(K key);
	public void put(K key, V value);
	public void remove(K key);
	public Set<K> getKeySet();
	public String getRegionName();
}
