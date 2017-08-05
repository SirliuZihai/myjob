package com.zihai.activiti.service.iml;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ADCTestDelegateImpl implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println(	"getCurrentActivityId==="+execution.getCurrentActivityId());
		System.out.println(	"getCurrentActivityName==="+execution.getCurrentActivityName());
		System.out.println(	"getId==="+execution.getId());
		System.out.println(	"getParentId==="+execution.getParentId());
		System.out.println(	"getSuperExecutionId==="+execution.getSuperExecutionId());
	}

}
