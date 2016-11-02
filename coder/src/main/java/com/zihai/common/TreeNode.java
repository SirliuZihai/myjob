package com.zihai.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 该数据结构对应easyUI树
 * */
public class TreeNode implements Serializable{

	private static final long serialVersionUID = 1L;
	//private TreeNode parent;
	private String id;
	private String text;
	private String state;
	private List<TreeNode> children;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	
	
}
