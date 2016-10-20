package com.zihai.controller.test;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zihai.service.print.PrintService;
import com.zihai.websocket.util.SessionUtils;

@Controller
@RequestMapping("/hellow")
public class HellowWorld  {
	private Logger log = LoggerFactory.getLogger("printEvents");
	
	@Resource(name="printEvents")
	private PrintService print;
	
	@RequestMapping("/world")
	public String hellow(String name,Model model){
		log.info("the name:"+name);
		print.print(name);
		model.addAttribute("username", "gook luc1s 先生"+name);
		return "test/hellow";
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
