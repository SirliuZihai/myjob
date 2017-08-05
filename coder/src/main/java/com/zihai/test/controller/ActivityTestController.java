package com.zihai.test.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zihai.activiti.service.ActivitiService;
import com.zihai.activiti.util.HistoryProcessInstanceDiagramCmd;
import com.zihai.common.Page;
import com.zihai.common.Result;
import com.zihai.common.TreeNode;

@Controller
@RequestMapping("/activitytest")
public class ActivityTestController {
	@Autowired
	private ActivitiService service;

	@Autowired
	private IdentityService identityService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private FormService formService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private JdbcTemplate jdbcTemplate_act;
	
	private String BPM_KEY = "test";
	
	private List<HashMap<String, String>> data;
	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

	
	
	ActivityTestController(){
		data = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> map1  = new HashMap<String,String>();
		map1.put("eventid", "20170723001");
		HashMap<String,String> map2  = new HashMap<String,String>();
		map2.put("eventid", "20170723002");
		data.add(map1);
		data.add(map2);
	}
	
	private void setidentity(){
		try {
			identityService.saveGroup(new GroupEntity("deptLeader"));
			identityService.saveGroup(new GroupEntity("hr"));
			identityService.saveUser(new UserEntity("liu"));
			identityService.saveUser(new UserEntity("zhi"));
			identityService.createMembership("zhi", "hr");
			identityService.createMembership("liu", "deptLeader");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/stop.do")
	@ResponseBody
	public Result stop(String eventid){
		try {
			String businessKey = BPM_KEY+"."+eventid;
			ProcessInstance p = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
			if(p==null)return Result.success("无流程");
			runtimeService.deleteProcessInstance(p.getProcessInstanceId(), "destroy");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(e.getMessage());
		}
		return Result.success("取消成功");
	}
	@RequestMapping("/start.do")
	@ResponseBody
	public Result start(String eventid){
		Map<String, Object> variables = new HashMap<String, Object>();
		//variables.put("listener1", new ExectuionListener());
		ArrayList<String> ins_list = new ArrayList<String>();
		ins_list.add("liu");
		ins_list.add("cao");
		variables.put("subInstanceList",ins_list);
		try {
			service.startProcess(BPM_KEY,eventid,variables);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(e.getMessage());
		}
		return Result.success("启动成功");
	}
	@RequestMapping("/submit.do")
	@ResponseBody
	public Result submit(String eventid,Integer arg,Integer agree,String userid,String reason){
		identityService.setAuthenticatedUserId(userid);
			String businessKey = BPM_KEY+"."+eventid;
			//Task t = taskService.createTaskQuery().processInstanceId("xxxx").active().taskCandidateOrAssigned(userid).singleResult();
			Task t;
			try {
				t = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).active().taskCandidateOrAssigned(userid).singleResult();
			} catch (Exception e) {
				t = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).active().taskCandidateOrAssigned(userid).list().get(0);
			}
			if(t==null)return Result.failure("无操作权限");
			runtimeService.setVariable(t.getProcessInstanceId(), "statue", arg);
			if(!StringUtils.isEmpty(reason))taskService.addComment(t.getId(), t.getProcessInstanceId(), reason);
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("gateway", agree);	
			service.completeTask(t.getId(), map);
			return Result.success("已审批");
	}
	@RequestMapping("/list")
	@ResponseBody
	public Page<?> list(int page,int rows){
		int start=(page-1)*rows;
		int end = page *rows;
		
		for(Map<String,String> map : data){
			String eventid = map.get("eventid");
			String businessKey = BPM_KEY+"."+eventid;
			ProcessInstance p = null;
			Task t = null;
			try {
				p = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
				if(p!=null)t = taskService.createTaskQuery().processInstanceId(p.getProcessInstanceId()).list().get(0);
			} catch (Exception e) {
				List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).list();
				for(ProcessInstance p1: list){
					runtimeService.deleteProcessInstance(p1.getProcessInstanceId(), "多余的");
				}
				e.printStackTrace();
			}
			if(p == null){
				map.put("instanceid", null);
				map.put("id", null);
				map.put("name", null);
				map.put("statue", null);
				map.put("reason", null);
				map.put("people", null);
				map.put("time", null);
				map.put("prepeople", null);
			}else{
				map.put("instanceid", p.getProcessInstanceId());
				map.put("id", p.getId());
				map.put("name", t.getName());
				map.put("statue", String.valueOf( runtimeService.getVariable(p.getId(), "statue")));
				map.put("reason", null);
				map.put("people", null);
				map.put("time", null);
				List<IdentityLink> links = taskService.getIdentityLinksForTask(t.getId());
				StringBuffer buf = new StringBuffer();
				for(IdentityLink li :links){
					buf.append(li.getUserId()+",");
				}
				map.put("prepeople", buf.substring(0, buf.length()-1));
				//最新批注
				List<String> list = jdbcTemplate_act.queryForList("select t.ID_ from act_hi_comment t where t.PROC_INST_ID_ = ? ORDER BY t.TIME_ desc",new String[]{p.getProcessInstanceId()},String.class);
				if(list.size()>0){
					Comment comment = taskService.getComment(list.get(0));
					map.put("reason", comment.getFullMessage());
					map.put("people", comment.getUserId());
					map.put("time",sf.format(comment.getTime()));
				}		
			}
			
		}	
		if(end>data.size())end = data.size();
		return new Page(data.size(),data.subList(start, end));
		
	}
	
	@RequestMapping(path="/showproccess")
	@ResponseBody
	public Result viewtraceImageByActiviti(@RequestParam String eventid,String prepeople,HttpServletResponse response){
		try {
			/*ProcessInstance p = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(BPM_KEY+"."+eventid).singleResult();
			String instanceid = null;
			if(p==null){
				List<HistoricProcessInstance> l = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(BPM_KEY+"."+eventid).orderByProcessInstanceEndTime().desc().list();// ;
				if(l.size()>0){
					instanceid = l.get(0).getId();
				}else{
					return Result.failure("无流程");
				}
			}else{
				instanceid = p.getProcessInstanceId();
			}*/
			String businessKey = BPM_KEY+"."+eventid;
			ProcessInstance p = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();			
			if(p==null){//查历史流程 从新到旧
				List<HistoricProcessInstance> his_pros = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).excludeSubprocesses(false).orderByProcessInstanceId().desc().list();
				if(his_pros.size()==0){
					return Result.failure("无流程");
				}else{
					service.traceImage(his_pros.get(0).getId(),response);
				}
			}else{//查运行的流程
				//通过关联人、查出 执行中的executionid
				List<HistoricTaskInstance> list_hisTask = historyService.createHistoricTaskInstanceQuery().processInstanceId(p.getProcessInstanceId()).taskInvolvedUser(prepeople).list();
				if(list_hisTask.size()==0){
					service.traceImage(p.getProcessInstanceId(),response);//看当前总流程
					return Result.failure("无相关流程");
				}
				String executionid = list_hisTask.get(0).getExecutionId();
				List<Execution> ex_list = runtimeService.createExecutionQuery().processInstanceId(p.getProcessInstanceId()).list();
				service.traceImage(list_hisTask.get(0).getProcessInstanceId(),response,getExecutions(ex_list,executionid));
			}

			
			return Result.success(null); 
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(e.getMessage(),null);
		}
	}
	
	@RequestMapping("/page.do")
	public String manage(){
		return "activity/test";
	}
	
	public String[] getExecutions(List<Execution> list,String executionid){
		List<String> lists = new ArrayList<String>();
		//获取
		Execution e = getExecution(list,executionid);
		//获取父id
		Execution ex =e;
		while(ex!=null){
			lists.add(ex.getId());
			ex = getExecution(list,ex.getParentId());	
		}
		//获取子id
		lists.addAll(getExecutionChilds(list,executionid));
		//排序
		Collections.sort(lists);
		String[] arr = lists.toArray(new String[lists.size()]);
		for(String str:arr){
			System.out.println(str);
		}		
		return arr;
		
	}
	private Execution getExecution(List<Execution> list,String executionid){
		for(Execution e : list){
			if(e.getId().equals(executionid))
				return e;	
		}
		return null;
	}
	
	private List<String> getExecutionChilds(List<Execution> list,String executionid){
		List<String> list1 = new ArrayList<String>();
		//加载次节点
		for(Execution e : list){
			if(executionid.equals(e.getParentId()))
				list1.add(e.getId());	
		}
		//加载次节点的子节点
		for(String childid :list1){
			list1.addAll(getExecutionChilds(list,childid));
		}
		return list1;
	}

}
