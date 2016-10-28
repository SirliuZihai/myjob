package com.zihai.util;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.zihai.entity.User;

/**
 * 
 * */
public class JdbcTool<T> {
	private static ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-mybatis.xml");

	private static NamedParameterJdbcDaoSupport dao = context.getBean(NamedParameterJdbcDaoSupport.class);
	
	public JdbcTool(){
	}
	
	public  List<T>  doSelect(String sql,Class<T> clazz){
    	RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(clazz);
    	List<T> list = dao.getJdbcTemplate().query(sql, rowMapper);
    	return  list ;
	}
	
	public List<T> queryForList(String sql,Class<T> clazz){
		return (List<T>) dao.getJdbcTemplate().queryForList("select id from areas",clazz);
	}
	
	public void doSql(String sql){
		dao.getJdbcTemplate().execute(sql);
	}
	public void doCall(String callString,CallableStatementCallback<T> action){
		dao.getJdbcTemplate().execute(callString, action);
	}
	public void example(){
		/*JdbcTool<Person> tool = new JdbcTool<Person>();
		List<Person> list = tool.doSelect("select * from Person", Person.class);*/
	}
	public static void main(String args[]){
		JdbcTool<String> tool = new JdbcTool<String>();
		List<String> list = dao.getJdbcTemplate().queryForList("select id from areas", String.class);
		System.out.println("size:"+list.size()+"11位："+list.get(11));
	}
}
