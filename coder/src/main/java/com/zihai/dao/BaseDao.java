package com.zihai.dao;

import java.util.List;

import com.zihai.entity.PageEntity;
import com.zihai.entity.User;

public interface BaseDao<T> {
	int deleteByPrimaryKey(String id);

    int insert(T record);

    int insertSelective(T entity);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(T entity);

    int updateByPrimaryKey(T entity);
    
    public List<T> list(PageEntity pe);
    
    public int count(T entity);
}
