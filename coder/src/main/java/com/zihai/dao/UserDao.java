package com.zihai.dao;

import com.zihai.entity.User;

public interface UserDao extends BaseDao<User>{
    User getAccount(User record);
    
}