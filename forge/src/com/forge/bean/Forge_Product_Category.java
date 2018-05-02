package com.forge.bean;

import java.io.Serializable;
import java.sql.Date;

/**
*Created by π˘—Ùon2018-04-23
*@Descrptionforge_product_category µÃÂ¿‡
*/ 


public class Forge_Product_Category  implements Serializable{
	private int id;
	private String name;
	private int parentId;
	private int type;
	private String iconClass;
	public Forge_Product_Category(){}
	public Forge_Product_Category(int id,String name,int parentId,int type,String iconClass){
	super();
	this. id=id;
	this. name=name;
	this. parentId=parentId;
	this. type=type;
	this. iconClass=iconClass;
}
	public void setId(int id){
	this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setName(String name){
	this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setParentId(int parentId){
	this.parentId=parentId;
	}
	public int getParentId(){
		return parentId;
	}
	public void setType(int type){
	this.type=type;
	}
	public int getType(){
		return type;
	}
	public void setIconClass(String iconClass){
	this.iconClass=iconClass;
	}
	public String getIconClass(){
		return iconClass;
	}
}

