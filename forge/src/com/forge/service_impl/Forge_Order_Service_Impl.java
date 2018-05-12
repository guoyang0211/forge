package com.forge.service_impl;

import java.io.Serializable;
import java.util.List;

import com.forge.bean.Forge_Order;
import com.forge.dao.Forge_Order_Dao;
import com.forge.dao_impl.Forge_Order_Dao_Impl;
import com.forge.service.Forge_Order_Service;

public class Forge_Order_Service_Impl implements Forge_Order_Service {
	Forge_Order_Dao dao=new Forge_Order_Dao_Impl();
	@Override
	public void add(Forge_Order t) {
		
		
	}

	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Forge_Order t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Forge_Order> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Forge_Order findById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Forge_Order findAll(int userId) {
	return	dao.findAll(userId);
		
	}

	@Override
	public void add(int userId, String loginName, String address, Double cost,
			String num2) {
		dao.add(userId,loginName,address,cost,num2);
		
	}

}
