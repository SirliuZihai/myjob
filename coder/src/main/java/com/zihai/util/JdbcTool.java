package com.zihai.util;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zihai.entity.PageEntity;

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
	/**
	 * preastatement,??,IN 情况
	 * */
/*	public  List<T>  doSelect(String sql,Class<T> clazz){
    	RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(clazz);
    	List<T> list = dao.getJdbcTemplate().query(sql, rowMapper);
    	return  list ;
	}*/
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
	public PageEntity<T> queryPage(String sql,int page,int rows,boolean hasoffset, Class<T> returnType) {
		//int start=(page-1)*rows;
				int start=(page-1)*rows+1;
				int end = page *rows;
		 StringBuffer pagesql=new StringBuffer(sql.length()+20);
		 StringBuffer totalsql=new StringBuffer(sql.length()+20);
		 totalsql.append("select count(*)  from (");
		 if(hasoffset){
		   // pagesql.append("select * from (select row_.*,rownum rownum_ from(");
			 pagesql.append(") t) t2 where t2.rownum_ between "+start+" and "+end);
		 }
		 else{
		    pagesql.append("select * from( ");
		 }
		 pagesql.append(sql);
		 totalsql.append(sql);
		 if(hasoffset){
		 // pagesql.append(") row_ where rownum<="+(start+rows)+") t where rownum_>"+start+"");
			 pagesql.append(") t) t2 where t2.rownum_ between "+start+" and "+end);
		 }
		else{
		 pagesql.append(") t  limit "+start+","+rows+"");
		}
		 totalsql.append(") t");
		 System.out.println(totalsql.toString());
		 System.out.println(pagesql.toString());
		 Integer total =
		 Integer.parseInt(dao.getJdbcTemplate().queryForList(totalsql.toString()
	        		).get(0).get("COUNT(*)").toString());
		 List<T> list=doSelect(pagesql.toString(), returnType);
		 return new PageEntity(total,list);
			} 
	public static void main(String args[]) throws PropertyVetoException, IOException{
		JdbcTemplate temp = (JdbcTemplate)context.getBean("jdbcTemplate");
		//连oracle
		if(true){
			InputStream stream = JdbcTool.class.getClassLoader().getResourceAsStream("property/db.properties");
			ComboPooledDataSource source = new ComboPooledDataSource();
			Properties property = new Properties();
			property.load(stream);
			source.setDriverClass(property.getProperty("db.driver"));
			source.setUser(property.getProperty("db.username"));
			source.setPassword(property.getProperty("db.password"));
			source.setJdbcUrl(property.getProperty("db.url"));
			temp.setDataSource(source);
		}
		
		
			List<String> result = temp.queryForList("select email from userinfo t where t.comcode = ? and t.deptcode = ? and t.useflag = '0' and t.indexuser = '1'",
					new String[]{"44030000","31000004"}, String.class);
					System.out.println(result.size());
		for(String str : result){
			//System.out.println(result.toString().)
		}
	}
}
