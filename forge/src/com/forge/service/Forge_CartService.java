package com.forge.service;

import java.io.Serializable;
import java.util.List;

import com.forge.bean.Forge_Cart;

public interface Forge_CartService extends BaseServise<Forge_Cart> {
	List<Forge_Cart> findByUserId(Serializable id);
	List<Forge_Cart> findAll(Serializable id);
	void update(int num,double price,Serializable userId,Serializable productId);
	void add(int id,int id2, int num, double price);
	void delete(String userId, String id);
	

}
