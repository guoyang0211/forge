package com.forge.bean;

import java.io.Serializable;

public class Forge_Cart implements Serializable{

	private String userId;  //用户Id
	private String productId;  //商品Id
	private int productNum;  //商品数量
	private double price;   //商品价钱
	
	public Forge_Cart() {
		super();
	}
	
	public Forge_Cart(String userId, String productId, int productNum,double price) {
		super();
		this.userId = userId;
		this.productId = productId;
		this.productNum = productNum;
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getuserId() {
		return userId;
	}
	public void setuserId(String userId) {
		this.userId = userId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getProductNum() {
		return productNum;
	}
	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}
	
	
}
