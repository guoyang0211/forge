package com.forge_CMS.bean;

import java.io.Serializable;
import java.util.Date;


/**
*Created by ����on2018-04-23
*@Descrptionforge_newsʵ����
*/ 


public class Forge_News  implements Serializable{
	private int id;
	private String title;
	private String content;
	private Date createTime;
	private String img;
	public Forge_News(){}
	public Forge_News(int id,String title,String content,Date createTime,String img){
	super();
	this. id=id;
	this. title=title;
	this. content=content;
	this. createTime=createTime;
	this. img=img;
}
	@Override
	public String toString() {
		return "Forge_News [id=" + id + ", title=" + title + ", content="
				+ content + ", createTime=" + createTime + ", img=" + img + "]";
	}
	public void setId(int id){
	this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setTitle(String title){
	this.title=title;
	}
	public String getTitle(){
		return title;
	}
	public void setContent(String content){
	this.content=content;
	}
	public String getContent(){
		return content;
	}
	public void setCreateTime(Date createTime){
	this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setImg(String img){
	this.img=img;
	}
	public String getImg(){
		return img;
	}
	
}

