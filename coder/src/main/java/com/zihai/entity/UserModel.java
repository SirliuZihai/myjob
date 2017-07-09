package com.zihai.entity;

import java.io.Serializable;
import java.util.List;

public class UserModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<User> users;
	private String oneleveltest;
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public UserModel(){
		
	}

	public String getOneleveltest() {
		return oneleveltest;
	}

	public void setOneleveltest(String oneleveltest) {
		this.oneleveltest = oneleveltest;
	}
}