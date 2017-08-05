package com.zihai.activiti.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public interface ACTtService extends JavaDelegate{
	public String doBusiness(DelegateExecution execution,String arg);
}
