package com.forge.bean;

import java.io.Serializable;
import java.util.Date;

public class Forge_UserTrack implements Serializable {
	private int userId; 
	private int productId;
	private Date viewTime;
	
	public Forge_UserTrack() {
		super();
	}

	public Forge_UserTrack(int userId, int productId, Date viewTime) {
		super();
		this.userId = userId;
		this.productId = productId;
		this.viewTime = viewTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Date getViewTime() {
		return viewTime;
	}

	public void setViewTime(Date viewTime) {
		this.viewTime = viewTime;
	}
	
	
	
	
}
