package com.zihai.util;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.record.MergeCellsRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
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
	/**
	 * 一对多的表导出的情况进行合并
	 * */
	public  void MergeCellForMainTable(Workbook workbook,Integer keycolumnNum,Integer beginColumn,Integer endColumn){
		Sheet sheet = workbook.getSheetAt(0);
		List<CellRangeAddress> merge_regionlist = new ArrayList<CellRangeAddress>();
		CellRangeAddress region = null; //上一次合并的region
		for(int j=beginColumn;j<=endColumn;j++ ){
			for(int i=1;i<=sheet.getLastRowNum();i++){
				Row row = sheet.getRow(i);
				Row lastrow = sheet.getRow(i-1);
				String rowkey = row.getCell(keycolumnNum).getStringCellValue();
				String lastrowkey = lastrow.getCell(keycolumnNum).getStringCellValue();
				if(i>1){
					Row lastbutoneRaw = sheet.getRow(i-2);
					String lastbutonerowkey = lastbutoneRaw.getCell(keycolumnNum).getStringCellValue();
					if(lastbutonerowkey.equals(rowkey)){
						region = merge_regionlist.get(merge_regionlist.size()-1);
						region.setLastRow(region.getLastRow()+1);
						continue;
					}
				}
				if(rowkey.equals(lastrowkey)){
					 region = new CellRangeAddress(i-1, i, j, j);
					 merge_regionlist.add(region);
				}
					
			}
		}
		for(CellRangeAddress region1:merge_regionlist){
			sheet.addMergedRegion(region1);
		}	
	}
	public static void main(String[] args) throws IOException, PropertyVetoException {
		//String sql = "select itme1  项目1,item2  项目2,itme3  项目3 from test";
		//new ExcelFromDatabase().WriteExcel("C:\\test.xlsx", sql);
		Workbook wb = new XSSFWorkbook(new FileInputStream(new File("C:\\test.xlsx")));
		new ExcelFromDatabase().MergeCellForMainTable(wb,2,0,2);
		FileOutputStream fileOut = new FileOutputStream("C:\\test2.xlsx");
		wb.write(fileOut);
	}

}
