package com.zihai.activiti.service.iml;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class ExectuionListener implements Serializable, TaskListener {

	

	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println(	"getAssignee==="+delegateTask.getAssignee());
		System.out.println(	"getCurrentActivityId==="+delegateTask.getExecution().getCurrentActivityId());
		System.out.println(	"getExecution==="+delegateTask.getExecution());
	}

}
