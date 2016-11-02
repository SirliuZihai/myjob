package com.zihai.service.iml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zihai.common.Page;
import com.zihai.common.TreeNode;
import com.zihai.dao.AreaDao;
import com.zihai.dao.UserDao;
import com.zihai.entity.Area;
import com.zihai.entity.PageEntity;
import com.zihai.entity.User;
import com.zihai.service.RedisService;
import com.zihai.service.UserinfoService;

@Service
public class UserinfoServiceiml implements UserinfoService {
	@Autowired
	private UserDao dao;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private RedisService<String,Object> redisService;
	
	
	@Override
	public Page<User> list(int pageSize, int Pagenum,User user) {
		List<User> list = dao.list(new PageEntity<User>(pageSize,Pagenum,user));
		return new Page<User>(dao.count(user), list);
	}

	@Override
	public List<TreeNode> getAreaTree(String id) {
		if(StringUtils.isEmpty(id))
			id="0";
		List<TreeNode> list = new ArrayList<TreeNode>();
		HashMap<String, Area> areas = getAreaMap();
		Iterator<Area> it = areas.values().iterator();
		while(it.hasNext()){
			Area area = it.next();
			if(id.equals(area.getParentid())){
				TreeNode node = new TreeNode();
				node.setId(area.getId());
				node.setText(area.getAreaname());
				node.setState("closed");
				list.add(node);
			}	
		}
		//insertNode(areas,areaTree);
 		return list;
	}
	
	/**
	 * 使用普通serivce属性变量
	 * *
	 */
	@Override
	public HashMap<String , Area> getAreaMap(){
		Object obj = redisService.get("AreaMap");
		if(obj!=null)
			return (HashMap<String , Area>) obj;
		List<Area> listAll = areaDao.selectAll();//取出所有地区
		HashMap<String,Area> areaMap = new HashMap<String,Area>();
		for(Area area :listAll){
			areaMap.put(area.getId(), area);
		}
		redisService.add("AreaMap", areaMap);
		return areaMap;
	}

	@Override
	public List<TreeNode> getAreaTree2() {
		Object obj = redisService.get("AreaTree");
		if(obj!=null)
			return ((TreeNode)obj).getChildren();
		TreeNode node = new TreeNode();
		node.setId("0");
		node.setState("closed");
		node.setText("中国");
		HashMap<String,Area> map = (HashMap<String, Area>) ((HashMap<String, Area>)getAreaMap()).clone();
		insertNode(map,node);
		redisService.add("AreaTree", node);
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
