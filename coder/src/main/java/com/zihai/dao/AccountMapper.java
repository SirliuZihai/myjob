package com.zihai.dao;

import com.zihai.entity.Account;

public interface AccountMapper {
    int insert(Account record);

    int insertSelective(Account record);
}