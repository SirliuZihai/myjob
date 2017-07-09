package com.zihai.entity;

import java.util.ArrayList;
import java.util.List;

public class PageEntity<T> {
	private int pageSize;
	private int pageNum;
	private int start;
	private T entity;
	private List<T> rows;
	private Integer total;
	/**
	 * 用于in()
	 * */
	private ArrayList<String> items;
	
	public PageEntity(int pageSize, int pageNum, T entity) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.entity = entity;
		this.start = (pageNum-1)*pageSize;
	}
	public PageEntity(Integer total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public ArrayList<String> getItems() {
		return items;
	}

	public void setItems(ArrayList<String> items) {
		this.items = items;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}


	
}
