package com.forge.service_impl;

import java.io.Serializable;
import java.util.List;

import com.forge.bean.Forge_Order_Detail;
import com.forge.dao.Forge_Order_Detail_Dao;
import com.forge.dao_impl.Forge_Order_Detail_Dao_Impl;
import com.forge.service.Forge_Order_Detail_Service;

public class Forge_Order_Detail_Service_Impl implements Forge_Order_Detail_Service{
	Forge_Order_Detail_Dao dao=new Forge_Order_Detail_Dao_Impl();
	@Override
	public void add(Forge_Order_Detail t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Forge_Order_Detail t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Forge_Order_Detail> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Forge_Order_Detail findById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int orderId, int productId, int num, double price) {
		// TODO Auto-generated method stub
		dao.add(orderId, productId, num, price);
	}

}
