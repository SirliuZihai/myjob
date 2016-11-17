package com.zihai.service;

public interface RedisService{
	public Object get(String key);
	public Boolean add(String key, Object value);
	public Boolean delete(String key);
	public String get2String(String key);
	public int getSize();
	public  void clear();
}
