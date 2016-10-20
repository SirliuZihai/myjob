package com.zihai.controller.test;


import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zihai.entity.TransLog;
import com.zihai.service.PrintService;
import com.zihai.service.TransService;
import com.zihai.websocket.util.SessionUtils;

@Controller
@RequestMapping("/hellow")
public class HellowWorld  {
	private Logger log = LoggerFactory.getLogger("printEvents");
	@Resource(name="printEvents")
	private PrintService print;
	@Autowired
	private TransService transService;
	
	@RequestMapping("/world")
	public String hellow(String name,Model model){
		log.info("the name:"+name);
		print.print(name);
		model.addAttribute("username", "gook luc1s 先生"+name);
		return "test/hellow";
	}
	@RequestMapping("/trans")
	@ResponseBody
	public String trans(int id){
		TransLog trans = new TransLog();
		trans.setUserFrom("zihai");
		trans.setUserTo("qq");
		trans.setRemark("测试");
		if(id==-1){
			trans.setAmt(new BigDecimal(55));
		}else{
			trans.setAmt(new BigDecimal(22));
		}
		try {
			transService.trans(trans);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
		return "OK";
	}
	/**
	 * @param relationId
	 * @param userCode
	 * @param message
	*/
	@RequestMapping("/sendmessage")
	//localhost:8080/coder/hellow/sendmessage.do?relationId=zihai&userCode=007&message=sended
	 public void broadcast(String relationId, int userCode, String message) {
	 if (SessionUtils.hasConnection(relationId, userCode)) {
	SessionUtils.get(relationId, userCode).getAsyncRemote().sendText(message);
	 } else {
	 throw new NullPointerException(SessionUtils.getKey(relationId, userCode) +"Connection does not exist");
	}

	}
}
