package com.forge.service_impl;

import java.util.ArrayList;
import java.util.List;

import com.forge.bean.Forge_Product;
import com.forge.bean.Forge_UserTrack;
import com.forge.bean.UserTrack;
import com.forge.dao.Forge_Product_Dao;
import com.forge.dao.Forge_UserTrack_Dao;
import com.forge.dao_impl.Forge_Product_Dao_Impl;
import com.forge.dao_impl.Forge_UserTrack_Dao_Impl;
import com.forge.service.Forge_UserTrack_Service;
import com.forge.util.JdbcUtil;

public class Forge_UserTrack_Service_Impl extends JdbcUtil implements Forge_UserTrack_Service{

	Forge_UserTrack_Dao dao = new Forge_UserTrack_Dao_Impl();
	@Override
	public void addTrack(int userId, String id) {
		dao.addTrack(userId,id);
	}
	@Override
	public List<UserTrack> queryTrack(int userId) {
		Forge_Product_Dao pdao = new Forge_Product_Dao_Impl();
		List<UserTrack> userTrack = new ArrayList();
		UserTrack a = null;
		Forge_Product product = null;
		
		List<Forge_UserTrack> tracks = dao.findByUserId(userId);
		for(int i=0;i<tracks.size();i++){
			product = pdao.findById(tracks.get(i).getProductId());
			a = new UserTrack();
			a.setProduct(product);
			a.setViewTime(tracks.get(i).getViewTime());
			a.setUserId(userId);
			userTrack.add(a);
		}
		return userTrack;
	}
	@Override
	public List<Forge_UserTrack> findByUserId(int userId) {
		
		return dao.findByUserId(userId);
	}
	@Override
	public void del(int userId, int id) {
		dao.del(userId,id);
	}

}
