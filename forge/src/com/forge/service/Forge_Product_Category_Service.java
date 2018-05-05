package com.forge.service;

import java.io.Serializable;
import java.util.List;

import com.forge.bean.Cart;
import com.forge.bean.Forge_Product;
import com.forge.bean.Forge_Product_Category;

public interface Forge_Product_Category_Service extends BaseServise<Forge_Product_Category>{
		//查询所有一级菜单
		List<Forge_Product_Category> findAll();
		//查询指定的
		Forge_Product_Category findById(Serializable id );
		
		//添加购物车
		void addCart(String id,Cart cart);
		
		//删除购物车
		void delCart(String id,Cart cart);
		//二级菜单
		List<Forge_Product_Category> findAll2(Serializable id);
		//查询所有三级菜单
		List<Forge_Product_Category> findAll3();
		List<Forge_Product> findByT3(Serializable id);
		
}
