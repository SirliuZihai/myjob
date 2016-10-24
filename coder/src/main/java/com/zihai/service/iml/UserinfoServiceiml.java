package com.zihai.service.iml;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zihai.common.Page;
import com.zihai.dao.UserDao;
import com.zihai.entity.PageEntity;
import com.zihai.entity.User;
import com.zihai.service.UserinfoService;

@Service
public class UserinfoServiceiml implements UserinfoService {
	@Autowired
	private UserDao dao;
	
	@Override
	public Page<User> list(int pageSize, int Pagenum,User user) {
		List<User> list = dao.list(new PageEntity<User>(pageSize,Pagenum,user));
		System.out.println(list.size());
		return new Page<User>(dao.count(user), list);
	}

}
