package com.zihai.service;

public interface RedisService<K,V> {
	public V get(String key);
	boolean add(String key, V value);
}
