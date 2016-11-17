package com.zihai.cache;

import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisCache implements Cache {
	private RedisTemplate<String, Object> redisTemplate;  
	private String name; 
	 
	 
	public RedisTemplate<String, Object> getRedisTemplate() {  
        return redisTemplate;  
    }
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {  
        this.redisTemplate = redisTemplate;  
    } 
	public void setName(String name) {
		 this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Object getNativeCache() {
		return redisTemplate;
	}

	@Override
	public ValueWrapper get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(Object key, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void evict(Object key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}
