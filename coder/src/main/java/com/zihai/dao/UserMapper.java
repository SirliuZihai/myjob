package com.zihai.dao;

import com.zihai.entity.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}