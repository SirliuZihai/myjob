package com.zihai.service.iml;

import javax.annotation.PostConstruct;

import org.junit.internal.Classes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.zihai.common.RedisCache;
import com.zihai.service.RedisService;


@Service
public class RedisServiceiml<K, V> implements RedisService<K, V> {
	private  Logger logger = LoggerFactory.getLogger(RedisServiceiml.class);

	@Autowired
	private RedisTemplate<String, V> redisTemplate ;

	
	@PostConstruct
	public void init() {
    }
		
	@Override
	public boolean add(String key, V value) {
		//redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(value.getClass()));
		boolean bool = redisTemplate.opsForValue().setIfAbsent(key, value);
		if(bool ==true)logger.info("add the key:"+key);
		return bool;
	}

	@Override
	public V get(String key) {
		V v = redisTemplate.opsForValue().get(key);
		if(v!=null)
			logger.info("hit the key"+key);
		return v ;
	}


}
