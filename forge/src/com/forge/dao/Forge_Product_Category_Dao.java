package com.forge.dao;

import java.util.List;

import com.forge.bean.Forge_Product;
import com.forge.bean.Forge_Product_Category;

public interface Forge_Product_Category_Dao extends BaseDao<Forge_Product_Category> {
	//��ѯ����
	List<Forge_Product_Category>findAll();
}
