package com.forge.bean;

import java.util.Date;

public class UserTrack {
	private int userId;  
	private Date viewTime;  //用户浏览时间 
	private Forge_Product product;  //商品记录
	public UserTrack() {
		super();
	}
	public UserTrack(int userId, Date viewTime, Forge_Product product) {
		super();
		this.userId = userId;
		this.viewTime = viewTime;
		this.product = product;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getViewTime() {
		return viewTime;
	}
	public void setViewTime(Date viewTime) {
		this.viewTime = viewTime;
	}
	public Forge_Product getProduct() {
		return product;
	}
	public void setProduct(Forge_Product product) {
		this.product = product;
	}
	
	
	
	
	
}
