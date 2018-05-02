package com.forge_CMS.dao;

import com.forge_CMS.bean.Forge_News;
import com.forge_CMS.bean.Forge_Users;



public interface Forge_News_Dao extends BaseDao<Forge_News> {
	Forge_News login(String loginName, String password);
}
