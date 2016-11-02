package com.zihai.common;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;


public class RedisCache implements Cache{
	private  Logger logger = LoggerFactory.getLogger(RedisCache.class);
	
	private static ClassPathXmlApplicationContext wac = new ClassPathXmlApplicationContext("spring/spring-redis.xml");
	
	private RedisTemplate<Object, Object> redisTemplate=(RedisTemplate<Object, Object>) wac.getBean("redisTemplate");
	
	
	private final String id;
	
	/**
     * The {@code ReadWriteLock}.
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public RedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        logger.debug("MybatisRedisCache:id=" + id);
        this.id = id;
    }
	

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void putObject(Object key, Object value) {
		redisTemplate.opsForValue().set(key, value);;
		
	}

	@Override
	public Object getObject(Object key) {
		if(redisTemplate==null)logger.info("redisTemplate is not injected");
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public Object removeObject(Object key) {
		redisTemplate.delete(key);
		return null;
	}

	@Override
	public void clear() {
		RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
		RedisConnection connection = factory.getConnection();
		connection.flushDb();
		connection.flushAll();
	}

	@Override
	public int getSize() {
		RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
		RedisConnection connection = factory.getConnection();
		return Integer.valueOf(connection.dbSize().toString());
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}

}
