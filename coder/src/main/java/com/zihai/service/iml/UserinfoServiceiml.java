package com.zihai.service.iml;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
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
	private RedisService redisService ;	
	
	@Override
	public Page<User> list(int pageSize, int Pagenum,User user) {
		HashMap<String,Area> map =getAreaMap();//从缓存读取地区Map
		Deque<String> query = new LinkedList<String>();//名称队列
		StringBuffer buffer = new StringBuffer();//缓存地区名称
		Area area;//地区类
		String str;//地区串
		String id = null;//地区id
		//加入地区IN的查询条件
		PageEntity<User> page = new PageEntity<User>(pageSize,Pagenum,user);
		if(user!=null&&user.getUserinfo()!=null&&StringUtils.isNotEmpty(id= user.getUserinfo().getArea())){
			TreeNode node = new TreeNode();
			node.setId(id);
			node.setChildren(getAreaTree(id));
			ArrayList<String> arr = new ArrayList<String>();
			getChildrenId(arr,node);
			page.setItems(arr);//加入条件
		}
		
		List<User> list = dao.list(page);//select
		
		//重写Areaname，通过递归（id->parentid->areaname）获得全称。
		for(User u :list){		
			id = u.getUserinfo().getAreainfo().getId();
			while(StringUtils.isNotEmpty(id)&&!"0".equals(id)){
				area =  map.get(id);
				query.addFirst(area.getAreaname());
				id = area.getParentid();
			}			
			while((str=query.poll()) != null){
				buffer.append(str+" ");
			}
			u.getUserinfo().getAreainfo().setAreaname(buffer.toString());
			query.clear();//清空队列
			buffer.delete(0, buffer.length());//清空
		}
		Integer count = null;
		
		if(user!=null&&user.getUserinfo()!=null)
			count = dao.count(page);
		if(count==null)
			count = JSON.parseObject(redisService.get2String("userinfoCountAll"), Integer.class);
		if(count==null){
			count = dao.count(page);
			redisService.add("userinfoCountAll", count);
		}
			
		return new Page<User>(count, list);//性能考虑，不返回总条数
	}
	/**
	 * 获得子树
	 * */
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
		HashMap<String, Area> obj =  JSON.parseObject(redisService.get2String("AreaMap"), new TypeReference<HashMap<String, Area>>(){});
		if(obj!=null)
			return obj;
		List<Area> listAll = areaDao.selectAll();//取出所有地区
		HashMap<String,Area> areaMap = new HashMap<String,Area>();
		for(Area area :listAll){
			areaMap.put(area.getId(), area);
		}
		redisService.add("AreaMap", areaMap);
		return areaMap;
	}
	
	//@Cacheable(value="listAreaTree",key="")
	@Override
	public List<TreeNode> getAreaTree2() {
		TreeNode obj = JSON.parseObject(redisService.get2String("AreaTree"), TreeNode.class);
		if(obj!=null)
			return obj.getChildren();
		TreeNode node = new TreeNode();
		node.setId("0");
		node.setState("closed");
		node.setText("中国");
		HashMap<String,Area> map = (HashMap<String, Area>) getAreaMap().clone();
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
	/**
	 * childrenId -> arr
	 * */
	private void getChildrenId(ArrayList<String> arr,TreeNode node){
		arr.add(node.getId());
		if(node.getChildren()!=null)
		for(TreeNode node_temp:node.getChildren()){
			getChildrenId(arr,node_temp);
		}
	}
	/**
	 * used for sql in()
	 * */
	private String formateArray(ArrayList<String> arr,String split){
		Iterator<String> it = arr.iterator();
		StringBuffer buf = new StringBuffer();
		while(it.hasNext()){
			buf.append(it.next()+split);
		}
		if(buf.length()>=split.length())return buf.substring(0, buf.length()-split.length());
		return null ;
		
	}
}
