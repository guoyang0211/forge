package com.forge.service;

import java.util.List;

import com.forge.bean.Forge_UserTrack;
import com.forge.bean.UserTrack;

public interface Forge_UserTrack_Service {

	void addTrack(int userId, String id);

	List<UserTrack> queryTrack(int userId);
	
	List<Forge_UserTrack> findByUserId(int userId);

	void del(int userId, int id);

}
