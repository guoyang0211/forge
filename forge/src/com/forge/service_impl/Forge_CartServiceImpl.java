package com.forge.service_impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.forge.bean.Forge_Cart;
import com.forge.dao.Forge_CartDao;
import com.forge.dao_impl.Forge_CartDaoImpl;

import com.forge.service.Forge_CartService;

public class Forge_CartServiceImpl implements Forge_CartService {
	//创建Logger对象
			Logger logger=Logger.getLogger(Forge_CartServiceImpl.class);
	Forge_CartDao dao = new Forge_CartDaoImpl();
	
	
	public void add(Forge_Cart t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Serializable id) {
		
		
	}

	@Override
	public void update(Forge_Cart t) {
		int rowNum = dao.update(t);
		if(rowNum>0){
			logger.info("修改成功");
		}else{
			logger.info("修改失败");
		}
		
		
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
		List<Forge_Cart> cartList = null;;
		cartList = dao.findByUserId(id);
		return cartList;
	}

	@Override
	public List<Forge_Cart> findAll(Serializable id) {
		
		return dao.findAll(id);
	}

	@Override
	public void update(int num, double price, Serializable userId,
			Serializable productId) {
		dao.update(num, price, userId, productId);
	}

	public void add(Serializable userId,Serializable productId,int num,double price) {
		dao.add(userId,productId,num,price);
		
	}


	
}
