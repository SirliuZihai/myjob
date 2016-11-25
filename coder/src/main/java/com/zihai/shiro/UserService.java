package com.zihai.shiro;

import java.util.Set;

import com.zihai.entity.User;

public interface UserService {
	public Boolean createUser(User user); //创建账户
	public Boolean changePassword(User user);//修改密码
	public void correlationRoles(Long userId, Long... roleIds); //添加用户-角色关系
	public void uncorrelationRoles(Long userId, Long... roleIds);// 移除用户-角色关系
	public User findByUsername(String username);// 根据用户名查找用户
}
