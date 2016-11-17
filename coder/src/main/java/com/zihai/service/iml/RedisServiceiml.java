package com.zihai.service.iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zihai.service.RedisService;

@Service
public class RedisServiceiml implements RedisService {
	@Autowired
	private  RedisTemplate<String, Object> redisTemplate;
	
	public  Boolean add(String key,Object value){
		return redisTemplate.execute( new RedisCallback<Boolean>(){
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.setNX(JSON.toJSONBytes(key), JSON.toJSONBytes(value));
			}
		},true);
	}
	public  Boolean delete(String key){
		return redisTemplate.execute( new RedisCallback<Boolean>(){
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.del(JSON.toJSONBytes(key))>0;
			}
		},true);
	}
	public  Object get(String key){
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
	public  void clear() {
		RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
		RedisConnection connection = factory.getConnection();
		connection.flushDb();
		connection.flushAll();
	}
	public  int getSize() {
		RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
		RedisConnection connection = factory.getConnection();
		return Integer.valueOf(connection.dbSize().toString());
	}
	/**
	 * return jsonString,用于转换javaObject
	 * */
	public  String get2String(String key){
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
