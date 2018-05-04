package com.forge.dao;

import java.io.Serializable;
import java.util.List;

import com.forge.bean.Forge_Product;

public interface Forge_Product_Dao extends BaseDao<Forge_Product> {
	//查询所有
	List<Forge_Product>findAll();
	//指定查询 
	Forge_Product findById(Serializable id);
}
