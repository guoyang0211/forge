package com.forge.dao;

import com.forge.bean.Forge_Users;

public interface Forge_Users_Dao extends BaseDao<Forge_Users> {
	Forge_Users login(String loginName, String password);

	Forge_Users findByName(String loginName);

}
