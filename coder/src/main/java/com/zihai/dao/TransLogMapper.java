package com.zihai.dao;

import com.zihai.entity.TransLog;

public interface TransLogMapper {
    int insert(TransLog record);

    int insertSelective(TransLog record);
}