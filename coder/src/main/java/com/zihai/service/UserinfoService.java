package com.zihai.service;

import com.zihai.common.Page;
import com.zihai.entity.User;

public interface UserinfoService {
	public Page<User> list(int pageSize,int Pagenum,User user);
}
