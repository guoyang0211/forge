package com.forge_CMS.dao;

import java.io.Serializable;

import com.forge_CMS.bean.Forge_Users;



public interface Forge_Users_Dao extends BaseDao<Forge_Users> {
	Forge_Users login(String loginName, String password);
	
	
}
