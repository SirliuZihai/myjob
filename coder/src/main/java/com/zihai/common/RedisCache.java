/*package com.zihai.common;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Resource;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

import com.zihai.util.RedisUtil;

public class RedisCache implements Cache{
	private  Logger logger = LoggerFactory.getLogger(RedisCache.class);

	private final String id;
	
	*//**
     * The {@code ReadWriteLock}.
     *//*
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
		RedisUtil.add(key, value);
		
	}

	@Override
	public Object getObject(Object key) {
		return RedisUtil.get(key);
	}

	@Override
	public Object removeObject(Object key) {
		 RedisUtil.delete(key);
		return null;
	}

	@Override
	public void clear() {
		 RedisUtil.clear();
	}

	@Override
	public int getSize() {
		return RedisUtil.getSize();
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}

}
*/