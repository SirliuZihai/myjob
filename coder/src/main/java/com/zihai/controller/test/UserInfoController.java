package com.zihai.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zihai.common.Page;
import com.zihai.entity.User;
import com.zihai.service.UserinfoService;

@Controller
@RequestMapping("/userinfo")
public class UserInfoController {
	@Autowired
	private UserinfoService service;
	
	@RequestMapping("/show.do")
	public String show(){
		return "test/userinfo";
	}
	
	@RequestMapping("/list.do")
	@ResponseBody
	public Page<User> list(int page,int rows,User user){
		return service.list(rows, page, user);
	}

}
