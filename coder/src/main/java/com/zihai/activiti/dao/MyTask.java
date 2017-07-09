package com.zihai.activiti.dao;

public class MyTask {
	private String companyCode;
	private String deptNo;
	private String taskName;
	private String taskTime;
	private String process;
	private String userId;
	private String hrefs;
	
	
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getHrefs() {
		return hrefs;
	}

	public void setHrefs(String hrefs) {
		this.hrefs = hrefs;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}

	@Override
	public String toString() {
		return "MyTask [companyCode=" + companyCode + ", deptNo=" + deptNo + ", taskName=" + taskName + ", taskTime="
				+ taskTime + ", process=" + process + ", hrefs=" + hrefs + "]";
	}

	


}
