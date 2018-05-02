package com.forge_CMS.bean;

import java.io.Serializable;
//后台管理员实体类
public class Forge_BGO implements Serializable {
	private int bgoid;
	
	private String loginName;
	private String password;
	@Override
	public String toString() {
		return "Forge_BGO [id=" + bgoid + ", userName=" + loginName + ", password="
				+ password + "]";
	}
	public Forge_BGO() {
		super();
	}
	public Forge_BGO(int id, String userName, String password) {
		super();
		this.bgoid = id;
		this.loginName = userName;
		this.password = password;
	}
	public int getId() {
		return bgoid;
	}
	public void setId(int id) {
		this.bgoid = id;
	}
	public String getUserName() {
		return loginName;
	}
	public void setUserName(String userName) {
		this.loginName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
