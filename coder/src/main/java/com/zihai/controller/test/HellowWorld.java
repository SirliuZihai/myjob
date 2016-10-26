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

import com.zihai.dao.TransLogDao;
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
	@Autowired
	private TransLogDao transLogDao;
	
	@RequestMapping("/world")
	public String hellow(String name,Model model){
		log.info("the name:"+name);
		print.print(name);
		//print.insertTest(num);
		model.addAttribute("username", "gook luc1s 先生"+name);
		return "test/hellow";
	}
	@RequestMapping("/trandshow.do")
	public String trandshow(String name,Model model){
		return "test/tradetest";
	}
	@RequestMapping("/trans.do")
	@ResponseBody
	public String trans(TransLog trans,String timesleep){
		System.out.println("get value is:"+trans.getUserFrom()+" "+trans.getUserTo()+" "+trans.getAmt()+" "+timesleep);
		try {
			trans.setTransId(transLogDao.getSeq());
			transLogDao.insertSelective(trans);
			if(!"".equals(timesleep)){
				transService.trans(trans,timesleep);
			}else{
				transService.trans(trans);
			}
			trans.setState("0");
		} catch (Exception e) {
			trans.setState("2");
			System.out.println(e.getMessage());
			return e.getMessage();
		}finally{
			transLogDao.updateByPrimaryKeySelective(trans);
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
