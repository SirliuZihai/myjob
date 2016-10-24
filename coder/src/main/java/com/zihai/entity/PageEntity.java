package com.zihai.entity;

public class PageEntity<T> {
	private int pageSize;
	private int pageNum;
	private int start;
	private T entity;
	
	public PageEntity(int pageSize, int pageNum, T entity) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.entity = entity;
		this.start = (pageNum-1)*pageSize;
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

	
}
