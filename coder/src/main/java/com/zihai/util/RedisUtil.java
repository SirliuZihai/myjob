package com.zihai.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;


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
	public static Object get(Object key){
		//if(redisTemplate==null)logger.info("redisTemplate is not injected");
		return redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] b  = JSON.toJSONBytes(key);
				if(connection.exists(b)==false)
					return null;
				return JSON.parse(connection.get(b));
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
	/**
	 * return jsonString,用于转换javaObject
	 * */
	public static String get2String(Object key){
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] b  = JSON.toJSONBytes(key);
				if(connection.exists(b)==false)
					return null;
				return JSON.toJSONString(JSON.parse(connection.get(b)));
			}
		},true);
	}
}
