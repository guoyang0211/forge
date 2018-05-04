package com.forge.service_impl;

import java.io.Serializable;
import java.util.List;

import com.forge.bean.Cart;
import com.forge.bean.Forge_Product;
import com.forge.dao.Forge_Product_Dao;
import com.forge.dao_impl.Forge_Product_Dao_Impl;
import com.forge.service.Forge_Product_Service;

public class Forge_Product_Service_Impl implements Forge_Product_Service {
	//创建Dao层实例
	private Forge_Product_Dao dao=new Forge_Product_Dao_Impl();
	@Override
	public void add(Forge_Product t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Forge_Product t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Forge_Product> findAll() {
		
		return dao.findAll();
	}

	@Override
	public Forge_Product findById(Serializable id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	public void addCart(String id, Cart cart) {
		//从数据库中获取商品
		Forge_Product product=dao.findById(id);
		//把商品放进购物车
		cart.addProduct(product);
		
	}

	@Override
	public void delCart(String id, Cart cart) {
		cart.getMap().remove(id);
		
	}

}
