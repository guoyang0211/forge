package com.forge.dao;

import java.util.List;

import com.forge.bean.Forge_Users;
import com.forge.bean.region;

public interface Forge_Users_Dao extends BaseDao<Forge_Users> {
	Forge_Users login(String loginName, String password);

	Forge_Users findByName(String loginName);

	List<region> findAddress(String parentId);

}
