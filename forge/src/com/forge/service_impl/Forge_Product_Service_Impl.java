package com.forge.service_impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.forge.bean.Cart;
import com.forge.bean.CartItem;
import com.forge.bean.Forge_Product;
import com.forge.dao.Forge_Product_Dao;
import com.forge.dao_impl.Forge_Product_Dao_Impl;
import com.forge.service.Forge_Product_Service;
import com.forge.util.MemcachedUtil;

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
	public void addCart(String id, Cart cart,int num) {
		//从数据库中获取商品
		Forge_Product product=dao.findById(id);
		System.out.println("========================>>>>>>>>"+product.getId());
		System.out.println("========================>>>>>>>>"+num);
		//把商品放进购物车
		cart.addProduct(product,num);
		
	}
	
	@Override
	public List<String> findBooksAjax(String name) {
		List<String> str=new ArrayList();
		List<Forge_Product> list=null;
		// 如果缓存中没有对象时，就进入数据库查询
		if (MemcachedUtil.getInstance().get("books") == null) {
			 list= dao.findBooksAjax();
			MemcachedUtil.getInstance().set("books", 1000, list);
		}
		list= (List<Forge_Product>) MemcachedUtil.getInstance().get("books");
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().indexOf(name)!=-1) {
				str.add(list.get(i).getName());
			}
		}
		System.out.println("222"+str);
		return str;
	}


	@Override
	public void delCart(String id, Cart cart) {
		cart.getMap().remove(id);
		
	}

}
