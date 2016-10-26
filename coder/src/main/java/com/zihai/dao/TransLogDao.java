package com.zihai.dao;

import com.zihai.entity.TransLog;

public interface TransLogDao {
    int deleteByPrimaryKey(String transId);

    int insert(TransLog record);

    int insertSelective(TransLog record);

    TransLog selectByPrimaryKey(String transId);

    int updateByPrimaryKeySelective(TransLog record);

    int updateByPrimaryKey(TransLog record);
    
    String getSeq();
}