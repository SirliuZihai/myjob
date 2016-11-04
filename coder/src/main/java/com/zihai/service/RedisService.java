package com.zihai.service;

public interface RedisService{
	public String get(String key);
	boolean add(String key, Object value);
}
