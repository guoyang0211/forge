package com.forge.dao;

import java.io.Serializable;
import java.util.List;

import com.forge.bean.Forge_Cart;


public interface Forge_CartDao extends BaseDao<Forge_Cart> {
	List<Forge_Cart> findByUserId(Serializable id);
	//�����û���id��ѯ�������ݿ������е���Ʒ
	List<Forge_Cart> findAll(Serializable id);
	
	public void add(Serializable userId,Serializable productId,int num,double price);
	int update(int num,double price,Serializable userId,Serializable productId);
	
}
