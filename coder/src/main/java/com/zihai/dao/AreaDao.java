package com.zihai.dao;

import java.util.List;

import com.zihai.entity.Area;

public interface AreaDao {
    int deleteByPrimaryKey(String id);

    int insert(Area record);

    int insertSelective(Area record);

    Area selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKey(Area record);
    
    List<Area> selectAll();
    
    List<String> getParentID();
}