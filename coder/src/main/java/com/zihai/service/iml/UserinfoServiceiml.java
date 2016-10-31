package com.zihai.service.iml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zihai.common.Page;
import com.zihai.common.TreeNode;
import com.zihai.dao.AreaDao;
import com.zihai.dao.UserDao;
import com.zihai.dao.UserInfoDao;
import com.zihai.entity.Area;
import com.zihai.entity.PageEntity;
import com.zihai.entity.User;
import com.zihai.service.UserinfoService;

@Service
public class UserinfoServiceiml implements UserinfoService {
	@Autowired
	private UserDao dao;
	@Autowired
	private AreaDao areaDao;
	
	private TreeNode areaTree;
	private HashMap<String, Area> areaMap;
	
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
	
	@Override
	public HashMap<String , Area> getAreaMap(){
		if(areaMap!=null)
			return areaMap;
		List<Area> listAll = areaDao.selectAll();//取出所有地区
		areaMap = new HashMap<String,Area>();
		for(Area area :listAll){
			areaMap.put(area.getId(), area);
		}
		return areaMap;
	}
	
	
}
