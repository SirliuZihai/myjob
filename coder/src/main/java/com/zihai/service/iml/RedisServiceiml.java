package com.zihai.service.iml;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zihai.service.RedisService;
import com.zihai.util.RedisUtil;


@Service
public class RedisServiceiml implements RedisService {
	private  Logger logger = LoggerFactory.getLogger(RedisServiceiml.class);

	//@Autowired
	//private RedisTemplate<String, V> redisTemplate ;

	
	@PostConstruct
	public void init() {
    }
		
	@Override
	public boolean add(String key, Object value) {
		//redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(value.getClass()));
		//boolean bool = redisTemplate.opsForValue().setIfAbsent(key, value);
		long time = System.currentTimeMillis();
		Boolean bool = RedisUtil.add(key, value);
		long time1 = System.currentTimeMillis()-time;
		if(bool ==true)logger.info("add the key:"+key+"and time use :"+time1);
		return bool;
	}
	
	/**
	 * return jsonString
	 * */
	@Override
	public String get(String key) {
	//	V v = redisTemplate.opsForValue().get(key);
		long time = System.currentTimeMillis();
		String v =  RedisUtil.get2String(key);
		long time1 = System.currentTimeMillis()-time;
		if(v!=null)
			logger.info("hit the key"+key+"and time use :"+time1);
		return v ;
	}


}
