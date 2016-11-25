package com.zihai.shiro;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zihai.dao.UserDao;
import com.zihai.entity.User;
import com.zihai.util.EncrypUtil;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userdao;
	
	public Boolean createUser(User user) {
		EncrypUtil.encryptPassword(user);
		return userdao.insertSelective(user)>0;
	}

	
	public Boolean changePassword(User user) {
		EncrypUtil.encryptPassword(user);
		return userdao.updateByPrimaryKeySelective(user)>0;
	}

	
	public void correlationRoles(Long userId, Long... roleIds) {
		// TODO Auto-generated method stub

	}

	
	public void uncorrelationRoles(Long userId, Long... roleIds) {
		// TODO Auto-generated method stub

	}

	
	public User findByUsername(String username) {
		return userdao.selectByPrimaryKey(username);
	}

	
	public Set<String> findRoles(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> findPermissions(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
