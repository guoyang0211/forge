package com.forge.bean;

import java.io.Serializable;

public class region implements Serializable{
	private double id;
	private String code;
	private String name;
	private double parentId;
	private double level;
	private double order;
	private String nameEn;
	private String shortname;
	public double getId() {
		return id;
	}
	public void setId(double id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getParentId() {
		return parentId;
	}
	public void setParentId(double parentId) {
		this.parentId = parentId;
	}
	public double getLevel() {
		return level;
	}
	public void setLevel(double level) {
		this.level = level;
	}
	public double getOrder() {
		return order;
	}
	public void setOrder(double order) {
		this.order = order;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	@Override
	public String toString() {
		return "region [id=" + id + ", code=" + code + ", name=" + name
				+ ", parentId=" + parentId + ", level=" + level + ", order="
				+ order + ", nameEn=" + nameEn + ", shortname=" + shortname
				+ "]";
	}
	public region(double id, String code, String name, double parentId,
			double level, double order, String nameEn, String shortname) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.parentId = parentId;
		this.level = level;
		this.order = order;
		this.nameEn = nameEn;
		this.shortname = shortname;
	}
	public region() {
		super();
	}

}
