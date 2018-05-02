package com.forge.bean;

import java.io.Serializable;
import java.sql.Date;

/**
*Created by π˘—Ùon2018-04-23
*@Descrptionforge_order µÃÂ¿‡
*/ 


public class Forge_Order  implements Serializable{
	private int id;
	private String userId;
	private String loginName;
	private String userAddress;
	private Date createTime;
	private double cost;
	private int status;
	private int type;
	private String serialNumber;
	public Forge_Order(){}
	public Forge_Order(int id,String userId,String loginName,String userAddress,Date createTime,double cost,int status,int type,String serialNumber){
	super();
	this. id=id;
	this. userId=userId;
	this. loginName=loginName;
	this. userAddress=userAddress;
	this. createTime=createTime;
	this. cost=cost;
	this. status=status;
	this. type=type;
	this. serialNumber=serialNumber;
}
	public void setId(int id){
	this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setUserId(String userId){
	this.userId=userId;
	}
	public String getUserId(){
		return userId;
	}
	public void setLoginName(String loginName){
	this.loginName=loginName;
	}
	public String getLoginName(){
		return loginName;
	}
	public void setUserAddress(String userAddress){
	this.userAddress=userAddress;
	}
	public String getUserAddress(){
		return userAddress;
	}
	public void setCreateTime(Date createTime){
	this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setCost(double cost){
	this.cost=cost;
	}
	public double getCost(){
		return cost;
	}
	public void setStatus(int status){
	this.status=status;
	}
	public int getStatus(){
		return status;
	}
	public void setType(int type){
	this.type=type;
	}
	public int getType(){
		return type;
	}
	public void setSerialNumber(String serialNumber){
	this.serialNumber=serialNumber;
	}
	public String getSerialNumber(){
		return serialNumber;
	}
}

