package com.zihai.activiti.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zihai.activiti.service.ActivitiService;
import com.zihai.common.Page;
import com.zihai.common.Result;

/**
 * @Description: TODO(工作流程定义与实例等资源处理类)
 * @author zh
 *
 */
@Controller
@RequestMapping("/activitiController")
public class ActivitiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiController.class);

	@Resource
	private ActivitiService activitiService;
	
	@Resource
	private RepositoryService repositoryService;
	// 主页待办任务
	/*@RequestMapping(path = "/daiBan")
	@ResponseBody
	public Page<?> daiBan(@RequestParam int page, @RequestParam int rows,HttpServletRequest request) {
		//UserInfo userInfo = CurrentUser.getCurrentUser();
		//Page<?> pagex = activitiService.daiBan(page, rows,userInfo,request);
		//return pagex;
	}*/

	// 部署流程
	@RequestMapping(path = "processDeploy", method = RequestMethod.POST)
	@ResponseBody
	public Result deployprocessDefine(String filename, MultipartFile file) {
		try {
			// 完成部署
			activitiService.saveNewDeploye(file, filename);
			return Result.success("部署成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure("部署失败，原因为:" + e);
		}
	}

	/**
	 * 流程部署查看
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(path = "viewProcessDeploy")
	@ResponseBody
	public Page<?> viewProcessDeploy(@RequestParam int page, @RequestParam int rows) {
		Page<?> pagelist = activitiService.viewProcessDeploy(page, rows);
		return pagelist;
	}
	/**
	 * 流程定义查看
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(path = "viewProdef")
	@ResponseBody
	public Page<?> viewProdef(@RequestParam int page, @RequestParam int rows) {
		Page<?> pagelist = activitiService.viewProdef(page, rows);
		return pagelist;
	}
	@RequestMapping(path = "/resource/process-def")
	@ResponseBody
	public Result viewResourceByDef(@RequestParam String processDefId,@RequestParam String type,HttpServletResponse response) {
		try {
			activitiService.resourceRead(processDefId,type, response);
			return Result.success(null);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(e.getMessage());
		}
	}
	/**
	 * 删除部署的流程，级联删除流程实例
	 * 
	 * @param deploymentId
	 *            流程部署ID
	 */
	@RequestMapping(value = "delDeploy")
	@ResponseBody
	public Result delDeploy(@RequestParam("deploymentId") String deploymentId) {
		try {
			repositoryService.deleteDeployment(deploymentId, true);
			return Result.success("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("删除失败，原因为:" + e);
			return Result.failure("删除失败，原因为:" + e);
		}
	}




	
	@RequestMapping(path="/viewtraceImageByActiviti")
	@ResponseBody
	public Result viewtraceImageByActiviti(@RequestParam String processInstanceId,HttpServletResponse response){
		try {
			activitiService.traceImageByActiviti(processInstanceId,response);
			return Result.success(null); 
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(e.getMessage(),null);
		}
	}

	@RequestMapping("/manage.do")
	public String manage(){
		return "activity/processDeployManage-list";
	}
	
/********do test****/
	
   
	
    
	
}
