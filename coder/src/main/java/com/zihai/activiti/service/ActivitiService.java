package com.zihai.activiti.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zihai.common.Page;
import com.zihai.entity.PageEntity;
import com.zihai.entity.UserInfo;


public interface ActivitiService {

	public Page<?> findMyStartFinishProcess(int page, int rows, String userid);

	void saveNewDeploye(MultipartFile file, String filename);

	InputStream findImageInputStream(String deploymentId, String imageName);

	void completeTask(String taskid, Map<String, Object> map);


	void traceImageByActiviti(String processInstanceId,HttpServletResponse response) throws IOException;
	
	public Page<?> viewProcessDeploy(int page, int rows);

	public Page<?> viewProdef(int page, int rows);

	public void resourceRead(String processDefId, String type, HttpServletResponse response) throws IOException;

	public ProcessInstance startProcess(String processType, String id, Map<String, Object> variables);

	void traceImage(String processid, HttpServletResponse response, String... executionids) throws IOException;

}