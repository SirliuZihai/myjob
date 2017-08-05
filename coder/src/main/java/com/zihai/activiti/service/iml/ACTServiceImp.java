package com.zihai.activiti.service.iml;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import com.zihai.activiti.service.ACTtService;

@Service("ACTService")
public class ACTServiceImp implements ACTtService {

	@Override
	public String doBusiness(DelegateExecution execution,String arg) {
		System.out.println("get the arg ==="+ arg);
		System.out.println("get the getProcessBusinessKey ==="+ execution.getProcessBusinessKey());
	//	execution.setVariable("gateway", execution.getVariable("agree"));
		return arg;
	}

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("execute ACT");
		
	}

}
