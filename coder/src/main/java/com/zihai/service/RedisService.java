package com.zihai.service;

public interface RedisService<K,V> {
	public V get(String key,Class<? extends Object> type);
	boolean add(String key, V value);
}
