package com.zihai.util;

import java.io.IOException;

import org.springframework.cglib.beans.BeanCopier;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BeanUtil {
	
	/**
	 * copy source to target
	 * */
	public void beancopy(Object source,Object target){
		 BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
	     copier.copy(source, target, null);
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException{
		Bean b = new ObjectMapper().readValue("{'name':'liu','age':'23'}",Bean.class);
		//Bean b = JSON.parseObject("{'name':'liu','age':23}", Bean.class);
		System.out.println(b.getAge());
	}
	
	public class Bean{
		private String name;
		private int age;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		
	}
}
