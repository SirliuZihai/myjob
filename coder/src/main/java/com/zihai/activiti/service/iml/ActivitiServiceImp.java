package com.zihai.activiti.service.iml;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.rest.diagram.services.ProcessInstanceHighlightsResource;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zihai.activiti.service.ActivitiService;
import com.zihai.activiti.util.HistoryProcessInstanceDiagramCmd;
import com.zihai.common.Page;

@Service
public class ActivitiServiceImp implements ActivitiService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiServiceImp.class);

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private FormService formService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private ManagementService managementService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	ProcessEngineFactoryBean processEngine;

	@Autowired
	ProcessEngineConfiguration processEngineConfiguration;
	
	@Autowired
	private ProcessInstanceHighlightsResource lightmap;

	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);





	/** 部署流程定义 */
	@Override
	public void saveNewDeploye(MultipartFile file, String filename) {
		ZipInputStream zipInputStream = null;
		try {
			// 2：将File类型的文件转化成ZipInputStream流
			zipInputStream = new ZipInputStream(file.getInputStream());
			// ZipInputStream zipInputStream = new ZipInputStream(new
			// FileInputStream(new File("D:/Desktop/leave.zip")));
			repositoryService.createDeployment()// 创建部署对象
					.name(filename)// 添加部署名称
					.addZipInputStream(zipInputStream)
					// .addClasspathResource("deploy/candidateUser.bpmn")
					.deploy();// 完成部署
		} catch (Exception e) {
			throw new RuntimeException("部署出现错误，原因是：" + e);
		} finally {
			if (zipInputStream != null) {
				try {
					zipInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/** 查询部署对象信息，对应表（act_re_deployment） */
	public List<Deployment> findDeploymentList() {
		List<Deployment> list = repositoryService.createDeploymentQuery()// 创建部署对象查询
				.orderByDeploymenTime().asc()//
				.list();
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<?> viewProcessDeploy(int page, int rows) {
		int start = (page - 1) * rows;
		List<Deployment> list = repositoryService.createDeploymentQuery().orderByDeploymentId().asc().listPage(start,
				rows);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Deployment d : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", d.getId());
			map.put("name", d.getName());
			map.put("time", dateFormat.format(d.getDeploymentTime()));
			// String
			// row="{id:"+d.getId()+",name:"+d.getName()+",time:"+d.getDeploymentTime()+"}";
			result.add(map);
		}
		long total = repositoryService.createDeploymentQuery().count();
		return new Page(new Integer(String.valueOf(total)), result);
	}

	/**
	 * 流程定义查看
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<?> viewProdef(int page, int rows) {
		int start = (page - 1) * rows;
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().orderByDeploymentId()
				.orderByProcessDefinitionVersion().asc().listPage(start, rows);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (ProcessDefinition d : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("deploymentId", d.getDeploymentId());
			map.put("defineId", d.getId());
			map.put("name", d.getName());
			map.put("key", d.getKey());
			map.put("resourceName", d.getResourceName());
			map.put("diagramResourceName", d.getDiagramResourceName());
			map.put("version", d.getVersion());
			result.add(map);
		}
		long total = repositoryService.createProcessDefinitionQuery().count();
		return new Page(new Integer(String.valueOf(total)), result);
	}

	/** 查询流程定义的信息，对应表（act_re_procdef） */
	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()// 创建流程定义查询
				.orderByProcessDefinitionVersion().asc()//
				.list();
		return list;
	}

	/** 使用部署对象ID，删除流程定义 */
	public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
	}

	/** 使用部署对象ID和资源图片名称，获取图片的输入流 */
	@Override
	public InputStream findImageInputStream(String deploymentId, String imageName) {
		return repositoryService.getResourceAsStream(deploymentId, imageName);
	}

	/**
	 * 启动流程
	 * 
	 * @param 流程类型
	 * @param 业务号
	 * @param 流程变量
	 */
	@Override
	public ProcessInstance startProcess(String processType, String id, Map<String, Object> variables) {
		// 格式：LeaveBill.id的形式（使用流程变量）
		String businessKey = processType + "." + id;
		//variables.put("businessKey", businessKey);
		// 使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		ProcessInstance p = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
		if(p!=null)throw new RuntimeException("已存在流程");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processType, businessKey, variables);
		return processInstance;
	}

	public void resourceRead(String processDefinitionId, String resourceType, HttpServletResponse response) throws IOException {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		String resourceName = "";
		if (resourceType.equals("image")) {
			resourceName = processDefinition.getDiagramResourceName();
		} else if (resourceType.equals("xml")) {
			resourceName = processDefinition.getResourceName();
		}
		InputStream resourceAsStream = null;
		try {
			resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
			byte[] b = new byte[1024];
			int len = -1;

			while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
				response.getOutputStream().write(b, 0, len);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (resourceAsStream != null) {
				resourceAsStream.close();
			}
		}
	}

	public void resourceReadByprocessId(String processId, String resourceType, HttpServletResponse response)
			throws IOException {
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processId).singleResult();
		if (historicProcessInstance != null) {
			String processDefinitionId = historicProcessInstance.getProcessDefinitionId();
			resourceRead(processDefinitionId, resourceType, response);
		}
	}

	/**
	 * 带图片跟踪的流程实例
	 */
	@Override
	public void traceImage(String processid,HttpServletResponse response,String... executionids) throws IOException {
		Command<InputStream> cmd = new HistoryProcessInstanceDiagramCmd(processid,executionids);
		InputStream is = managementService.executeCommand(cmd);
		int len = 0;
		byte[] b = new byte[1024];

		while ((len = is.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	/**
	 * 查找个人任务，返回实例号
	 */
	public List<String> findMyTaskReturnProcessInstanceId(int page, int rows, String userId) {
		List<String> l = new ArrayList<String>();
		int start = (page - 1) * rows;
		List<Task> list = taskService.createTaskQuery().taskCandidateOrAssigned(userId).orderByTaskCreateTime().asc()
				.listPage(start, rows);
		for (Task t : list) {
			String processInstanceId = t.getProcessInstanceId();
			l.add(processInstanceId);
		}
		return l;
	}

	/**
	 * 查找个人任务，返回任务号
	 * 
	 * @param page
	 * @param rows
	 * @param u
	 * @return 任务号
	 */
	public List<String> findMyTaskReturnTaskId(int page, int rows, String userId) {
		List<String> l = new ArrayList<String>();
		int start = (page - 1) * rows;
		List<Task> list = taskService.createTaskQuery().taskCandidateOrAssigned(userId).orderByTaskCreateTime().asc()
				.listPage(start, rows);
		for (Task t : list) {
			l.add(t.getId());
		}
		return l;
	}


	/**
	 * 查找个人所有任务
	 * 
	 * @param u
	 * @return
	 */
	public List<String> findMyTaskReturnTaskId(String u) {
		List<String> l = new ArrayList<String>();
		List<Task> list = taskService.createTaskQuery().taskAssignee(u).orderByTaskCreateTime().asc().list();
		for (Task t : list) {
			l.add(t.getId());
		}
		return l;
	}

	public long findMyTaskReturnTaskIdCount(String userId) {
		long l = taskService.createTaskQuery().taskCandidateOrAssigned(userId).count();
		return l;
	}

	/**
	 * 查找个人组任务，返回实例号
	 */
	public List<String> findMyGroupTaskReturnProcessInstanceId(int page, int rows, String userId) {
		List<String> l = new ArrayList<String>();
		int start = (page - 1) * rows;
		List<Group> groups = identityService.createGroupQuery().groupMember(userId).list();
		List<String> groupIds = new ArrayList<String>();
		for (Group group : groups) {
			groupIds.add(group.getId());
		}
		if (groupIds == null || "".equals(groupIds) || groupIds.size() == 0) {
			return null;
		}
		List<Task> list = taskService.createTaskQuery().taskCandidateGroupIn(groupIds).orderByTaskCreateTime().asc()
				.listPage(start, rows);
		for (Task t : list) {
			String processInstanceId = t.getProcessInstanceId();
			l.add(processInstanceId);
		}
		return l;
	}

	/**
	 * 查找个人组任务，返回任务ID
	 * 
	 * @param page
	 * @param rows
	 * @param u
	 * @return 任务ID
	 */
	public List<String> findMyGroupTaskReturnTaskId(int page, int rows, String userId) {
		List<String> l = new ArrayList<String>();
		int start = (page - 1) * rows;
		List<Group> groups = identityService.createGroupQuery().groupMember(userId).list();
		List<String> groupIds = new ArrayList<String>();
		for (Group group : groups) {
			groupIds.add(group.getId());
		}
		if (groupIds == null || "".equals(groupIds) || groupIds.size() == 0) {
			return null;
		}
		List<Task> list = taskService.createTaskQuery().taskCandidateGroupIn(groupIds).orderByTaskCreateTime().asc()
				.listPage(start, rows);
		for (Task t : list) {
			l.add(t.getId());
		}
		return l;
	}

	public List<String> findMyGroupTaskReturnTaskId(String userId) {
		List<String> l = new ArrayList<String>();
		List<Group> groups = identityService.createGroupQuery().groupMember(userId).list();
		List<String> groupIds = new ArrayList<String>();
		for (Group group : groups) {
			groupIds.add(group.getId());
		}
		if (groupIds == null || "".equals(groupIds) || groupIds.size() == 0) {
			return null;
		}
		List<Task> list = taskService.createTaskQuery().taskCandidateGroupIn(groupIds).orderByTaskCreateTime().asc()
				.list();
		for (Task t : list) {
			l.add(t.getId());
		}
		return l;
	}



	public long findMyGroupTaskReturnTaskIdCount(String userId) {
		List<Group> groups = identityService.createGroupQuery().groupMember(userId).list();
		List<String> groupIds = new ArrayList<String>();
		for (Group group : groups) {
			groupIds.add(group.getId());
		}
		if (groupIds == null || "".equals(groupIds) || groupIds.size() == 0) {
			return 0;
		}
		long l = taskService.createTaskQuery().taskCandidateGroupIn(groupIds).count();
		return l;
	}

	/**
	 * 查找个人已完成历史任务，返回实例号
	 */
	public List<String> findMyHiTask(int page, int rows, String userId) {
		List<String> l = new ArrayList<String>();
		int start = (page - 1) * rows;
		List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId)
				.finished().orderByTaskCreateTime().asc().listPage(start, rows);
		for (HistoricTaskInstance t : list) {
			String processInstanceId = t.getProcessInstanceId();
			l.add(processInstanceId);
		}
		return l;
	}

	

	/**
	 * 创建用户组
	 * 
	 * @param 用户组id
	 * @param 用户组名称
	 * @param 用户组类型
	 */
	public void createGroup(String groupId, String groupName, String groupType) {
		Group group = identityService.newGroup(groupId);
		group.setName(groupName);
		group.setType(groupType);
		identityService.saveGroup(group);
	}

	/**
	 * 更新用户组
	 * 
	 * @param 用户组id
	 * @param 用户组名称
	 * @param 用户组类型
	 */
	public void updateGroup(String groupId, String groupName, String groupType) {
		Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
		if (group == null || "".equals(group)) {
			group = identityService.newGroup(groupId);
		}
		group.setName(groupName);
		group.setType(groupType);
		identityService.saveGroup(group);
	}

	/**
	 * 删除用户组
	 * 
	 * @param 用户组id
	 */
	public void deleteGroup(String groupId) {
		identityService.deleteGroup(groupId);
	}

	/**
	 * 创建用户组关系
	 * 
	 * @param 用户id
	 * @param 用户组id
	 */
	public void createGroupMemberShip(String userId, String groupId) {
		identityService.createMembership(userId, groupId);
	}

	/**
	 * 获取连线名称和代码
	 */
	public List<String> findOutComeListByTaskId(String taskId) {
		// 返回存放连线的名称集合
		List<String> list = new ArrayList<String>();
		// 1:使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)// 使用任务ID查询
				.singleResult();
		// 2：获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		// 3：查询ProcessDefinitionEntiy对象
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);
		// 使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		// 获取当前活动的id
		String activityId = pi.getActivityId();
		// 4：获取当前的活动
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		// 5：获取当前活动完成之后连线的名称
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if (pvmList != null && pvmList.size() > 0) {
			for (PvmTransition pvm : pvmList) {
				String name = (String) pvm.getProperty("name");
				if (StringUtils.isNotBlank(name)) {
					list.add(name);
				} else {
					list.add("默认提交");
				}
			}
		}
		return list;
	}



	public String findProcessInstanceIdByTaskId(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		return processInstanceId;
	}

	/**
	 * 签收任务
	 * 
	 * @param 流程实例ID
	 * @param 用户ID
	 */
	public void claimTaskByProcessInstanceId(String processInstanceId, String userId) {
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		claimTaskByTaskId(task.getId(), userId);
	}


	/**
	 * 得到任务id
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public String qryTaskId(String processInstanceId) {
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		String id = task.getId();
		return id;
	}

	/**
	 * 签收任务
	 * 
	 * @param 任务ID
	 * @param 用户ID
	 */
	public void claimTaskByTaskId(String taskId, String userId) {
		taskService.claim(taskId, userId);
	}





	

	


	/**
	 * 通过任务id来找流程实例id
	 * 
	 * @param taskId
	 * @return 流程实例id
	 */
	public String getProcessIdByTaskId(String taskId) {
		HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		return task.getProcessInstanceId();
	}


	/**
	 * 通过流程实例id来删除实例
	 * 
	 * @param processInstanceId
	 * @param reason
	 */
	public void destoryProcessIdByProcessInstanceId(String processInstanceId, String reason) {
		runtimeService.deleteProcessInstance(processInstanceId, reason);
	}

	public void TransTaskByTaskId(String taskId, String userId) {
		taskService.setAssignee(taskId, userId);
	}

	






	

	@Override
	public void traceImageByActiviti(String executionId, HttpServletResponse response) throws IOException {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(executionId).singleResult();
		// String executionId=processInstance.getActivityId();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		//List<String> activeActivityIds = runtimeService.getActiveActivityIds(executionId);
		List<String> activeActivityIds = new ArrayList<String>();
		List<String> flows = new ArrayList<String>();
		ObjectNode ob = lightmap.getHighlighted(executionId);
		for(JsonNode str1 : ob.get("activities")){
			activeActivityIds.add(str1.asText());
		};
		for(JsonNode str1 : ob.get("flows")){
			flows.add(str1.asText());
		};
		
		// 使用spring注入引擎请使用下面的这行代码
		processEngineConfiguration = processEngine.getProcessEngineConfiguration();
		Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

		ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
		InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds,flows, 
				processEngine.getProcessEngineConfiguration().getActivityFontName(),
				processEngine.getProcessEngineConfiguration().getLabelFontName(), 
				null, null, 1.0);
		// 输出资源内容到相应对象
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}



	@Override
	public void completeTask(String taskid, Map<String, Object> map) {
		taskService.complete(taskid, map);
	}

	@Override
	public Page<?> findMyStartFinishProcess(int page, int rows, String userid) {
		// TODO Auto-generated method stub
		return null;
	}




}
