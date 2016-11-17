package com.zihai.common;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

public class AspectBean implements MethodBeforeAdvice,AfterReturningAdvice{
	private long timebefore;
	
	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		timebefore = System.currentTimeMillis();
	}

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		System.out.println(method.getName()+"use time:"+(System.currentTimeMillis()-timebefore));		
	}

}
