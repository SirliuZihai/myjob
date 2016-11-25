package com.zihai.shiro;

import org.apache.shiro.authz.Permission;


/**
 * 权限
 * */
public interface PermissionService {
	public Permission createPermission(Permission permission);
	public void deletePermission(Long permissionId);
}