package com.zihai.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;


public class RedisUtil {
	private static ClassPathXmlApplicationContext wac = new ClassPathXmlApplicationContext("spring/spring-redis.xml");
	private static RedisTemplate<Object, Object> redisTemplate=(RedisTemplate<Object, Object>) wac.getBean("redisTemplate");
	
	public static Boolean add(Object key,Object value){
		return redisTemplate.execute( new RedisCallback<Boolean>(){
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.setNX(JSON.toJSONBytes(key), JSON.toJSONBytes(value));
			}
		},true);
	}
	public static Boolean delete(Object key){
		return redisTemplate.execute( new RedisCallback<Boolean>(){
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.del(JSON.toJSONBytes(key))>0;
			}
		},true);
	}
	public static Object get(Object key ,Class<? extends Object>...type){
		//if(redisTemplate==null)logger.info("redisTemplate is not injected");
		return redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] b  = JSON.toJSONBytes(key);
				if(connection.exists(b)==false)
					return null;
				if(type.length==0)
					return JSON.parse(connection.get(b));
				return JSON.parseObject(connection.get(b),type[0]);
			}
		},true);
	}
	public static void clear() {
		RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
		RedisConnection connection = factory.getConnection();
		connection.flushDb();
		connection.flushAll();
	}
	public static int getSize() {
		RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
		RedisConnection connection = factory.getConnection();
		return Integer.valueOf(connection.dbSize().toString());
	}
}
