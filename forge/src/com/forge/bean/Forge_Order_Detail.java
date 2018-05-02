package com.forge.bean;

import java.io.Serializable;
import java.sql.Date;

/**
*Created by π˘—Ùon2018-04-23
*@Descrptionforge_order_detail µÃÂ¿‡
*/ 


public class Forge_Order_Detail  implements Serializable{
	private int id;
	private int orderId;
	private int productId;
	private int quantity;
	private double cost;
	public Forge_Order_Detail(){}
	public Forge_Order_Detail(int id,int orderId,int productId,int quantity,double cost){
	super();
	this. id=id;
	this. orderId=orderId;
	this. productId=productId;
	this. quantity=quantity;
	this. cost=cost;
}
	public void setId(int id){
	this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setOrderId(int orderId){
	this.orderId=orderId;
	}
	public int getOrderId(){
		return orderId;
	}
	public void setProductId(int productId){
	this.productId=productId;
	}
	public int getProductId(){
		return productId;
	}
	public void setQuantity(int quantity){
	this.quantity=quantity;
	}
	public int getQuantity(){
		return quantity;
	}
	public void setCost(double cost){
	this.cost=cost;
	}
	public double getCost(){
		return cost;
	}
}

