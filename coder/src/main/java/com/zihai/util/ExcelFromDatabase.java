package com.zihai.util;

import java.beans.PropertyVetoException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ExcelFromDatabase {

	public void WriteExcel(String fileName,String sql) throws IOException, PropertyVetoException{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-mybatis.xml");
		JdbcTemplate temp = (JdbcTemplate)context.getBean("jdbcTemplate");
		//连oracle
		if(true){
			InputStream stream = getClass().getClassLoader().getResourceAsStream("property/db.properties");
			ComboPooledDataSource source = new ComboPooledDataSource();
			Properties property = new Properties();
			property.load(stream);
			source.setDriverClass(property.getProperty("db.driver"));
			source.setUser(property.getProperty("db.username"));
			source.setPassword(property.getProperty("db.password"));
			source.setJdbcUrl(property.getProperty("db.url"));
			temp.setDataSource(source);
		}
		//String sql = "select itme1 项目1,item2 项目2 from test";
		SqlRowSet set = temp.queryForRowSet(sql);
		SqlRowSetMetaData  meta =set.getMetaData();
		
		//Excel
		Workbook wb = new XSSFWorkbook();
	    FileOutputStream fileOut = new FileOutputStream(fileName);
	    Sheet sheet = wb.createSheet("sheet1");
	    Row row = sheet.createRow(0);
	    for(int i=1;i<=meta.getColumnCount();i++){
	    	row.createCell(i-1).setCellValue(meta.getColumnName(i));
	    	System.out.println(meta.getColumnType(i));
	    	System.out.println(meta.getColumnTypeName(i));
	    	System.out.println(meta.getColumnLabel(i));
	    	System.out.println(meta.getColumnClassName(i));
	    }
	    do{
			set.next();
			row = sheet.createRow(set.getRow());
			for(int j=1;j<=meta.getColumnCount();j++){
				String type = meta.getColumnClassName(j);
				if(type.equals(BigDecimal.class.getName())){
					BigDecimal num = set.getBigDecimal(j);
					if(num.toString().indexOf(".")==-1){
						row.createCell(j-1).setCellValue(num.toString());}
					else row.createCell(j-1).setCellValue(num.setScale(2, RoundingMode.HALF_UP).toString());
				}else if(type.equals(String.class.getName())){
					row.createCell(j-1).setCellValue(set.getString(j));
				}else if(type.equals(Date.class.getName())){
					row.createCell(j-1).setCellValue(set.getDate(j));
				}
					
			}			
		}while(!set.isLast());
	    wb.write(fileOut);
	    fileOut.close();	
	    wb.close();
	}
	
	public static void main(String[] args) throws IOException, PropertyVetoException {
		String sql = "select itme1  项目1,item2  项目2,itme3  项目3 from test";
		new ExcelFromDatabase().WriteExcel("C:\\test.xlsx", sql);

	}

}
