package com.zihai.service;

import java.util.List;
import java.util.Map;

import com.zihai.common.Page;
import com.zihai.common.TreeNode;
import com.zihai.entity.Area;
import com.zihai.entity.User;

public interface UserinfoService {
	public List<TreeNode> getAreaTree(String id);
	public Map<String,Area> getAreaMap();
	public Page<User> list(int pageSize,int Pagenum,User user);
}
