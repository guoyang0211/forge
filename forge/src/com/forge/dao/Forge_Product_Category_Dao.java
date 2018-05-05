package com.forge.dao;

import java.io.Serializable;
import java.util.List;

import com.forge.bean.Forge_Product;
import com.forge.bean.Forge_Product_Category;

public interface Forge_Product_Category_Dao extends BaseDao<Forge_Product_Category> {
	//查询所有
	List<Forge_Product_Category>findAll();
	//查询所有的二级菜单
	List<Forge_Product_Category>findAll2(Serializable id);
	//三级菜单
	List<Forge_Product_Category>findAll3();
	List<Forge_Product> findByT3(Serializable id);
	
}
