package com.forge.dao_impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.forge.bean.Forge_Cart;
import com.forge.dao.Forge_CartDao;

import com.forge.service.Forge_CartService;

public class Forge_CartServiceImpl implements Forge_CartService {
	
	Forge_CartDao dao = new Forge_CartDaoImpl();
	
	@Override
	public void add(Forge_Cart t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Forge_Cart t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Forge_Cart> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Forge_Cart findById(Serializable id) {
		return null;
	}

	@Override
	public List<Forge_Cart> findByUserId(Serializable id) {
		List<Forge_Cart> cartList = new ArrayList();
		cartList = dao.findByUserId(id);
		return cartList;
	}

	@Override
	public List<Forge_Cart> findAll(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(int num, double price, Serializable userId,
			Serializable productId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(Serializable id, Serializable id2, int num, double price) {
		// TODO Auto-generated method stub
		
	}

}
