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
	
	private TreeNode nodeCache;

	
	@RequestMapping("/show.do")
	public String show(){
		return "test/userinfo";
	}
	
	@RequestMapping("/list.do")
	@ResponseBody
	public Page<User> list(int page,int rows,User user){
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
		ArrayList<TreeNode> array = new ArrayList<TreeNode>();
		if(nodeCache!=null){
			array.add(nodeCache);
			return array;
		}
			
		TreeNode node = new TreeNode();
		node.setId("0");
		node.setState("closed");
		node.setText("中国");
		HashMap<String,Area> map = (HashMap<String, Area>) ((HashMap<String, Area>) service.getAreaMap()).clone();
		insertNode(map,node);
		nodeCache = node;
		//array.add(node);
		return node.getChildren();
	}
	
	//组装树节点
		private void insertNode(HashMap<String, Area> areas,TreeNode node){
			Iterator<String> it = areas.keySet().iterator();
			while(it.hasNext()){
				String id = it.next();
				Area area = areas.get(id);
				if(node.getId().equals(area.getParentid())){
					if(node.getChildren()==null)
						node.setChildren(new ArrayList<TreeNode>());
					TreeNode node2 = new TreeNode();
					//node2.setParentNode(node);
					node2.setId(area.getId());
					node2.setState("closed");
					node2.setText(area.getAreaname());
					node.getChildren().add(node2);
					it.remove();
				}
			}
			if(node.getChildren()==null){
				node.setState("open");
				return;
			}
			for(TreeNode node3:node.getChildren()){
				insertNode(areas,node3);
			}
		}
}
