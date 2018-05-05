package com.forge.service_impl;

import java.io.Serializable;
import java.util.List;

import com.forge.bean.Cart;
import com.forge.bean.Forge_Product;
import com.forge.bean.Forge_Product_Category;
import com.forge.dao.Forge_Product_Category_Dao;
import com.forge.dao_impl.Forge_Product_Category_Dao_Impl;
import com.forge.service.Forge_Product_Category_Service;

public class Forge_Product_Category_Service_Impl implements Forge_Product_Category_Service{
	Forge_Product_Category_Dao dao=new Forge_Product_Category_Dao_Impl();
	@Override
	public void add(Forge_Product_Category t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Forge_Product_Category t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Forge_Product_Category> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public Forge_Product_Category findById(Serializable id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	public void addCart(String id, Cart cart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delCart(String id, Cart cart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Forge_Product_Category> findAll2(Serializable id) {
		
		return dao.findAll2( id);
	}

	@Override
	public List<Forge_Product_Category> findAll3() {
		
		return dao.findAll3();
	}

	@Override
	public List<Forge_Product> findByT3(Serializable id) {
		
		return dao.findByT3(id);
	}

	

}
