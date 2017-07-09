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
import com.zihai.entity.UserInfo;


public interface ActivitiService {

	Page<?> findMyStartFinishProcess(int page, int rows, UserInfo u);

	void saveNewDeploye(MultipartFile file, String filename);

	InputStream findImageInputStream(String deploymentId, String imageName);

	void completeTask(String taskid, Map<String, Object> map);

	void completeTask(String processInstanceId, String userId);

	String getProcessDefIdByTaskId(String taskId);

	void traceImageByActiviti(String processInstanceId, HttpServletResponse response) throws IOException;
}