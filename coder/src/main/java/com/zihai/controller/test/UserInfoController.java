package com.zihai.controller.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zihai.common.Page;
import com.zihai.common.TreeNode;
import com.zihai.entity.Area;
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
		if(user.getUserinfo()!=null)
			System.out.println(user.getUserinfo().getName()+" "+user.getUserinfo().getSex()+" "+user.getUserinfo().getArea());
		return service.list(rows, page, user);
	}
	
	@RequestMapping("/areaAll.do")
	@ResponseBody
	public List<TreeNode> areaAll(String id){
		return service.getAreaTree(id);
	}
	
	@RequestMapping("/areaAll2.do")
	@ResponseBody
	public List<TreeNode> areaAll2(){
		return service.getAreaTree2();
	}
	
	
}
