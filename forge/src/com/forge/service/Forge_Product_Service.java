package com.forge.service;

import java.io.Serializable;
import java.util.List;

import com.forge.bean.Cart;
import com.forge.bean.Forge_Product;

public interface Forge_Product_Service extends BaseServise<Forge_Product> {
	//查询所有
	List<Forge_Product> findAll();
	//查询指定的
	Forge_Product findById(Serializable id );
	
	//添加购物车
	void addCart(String id,Cart cart);
	
	//删除购物车
	void delCart(String id,Cart cart);
}
