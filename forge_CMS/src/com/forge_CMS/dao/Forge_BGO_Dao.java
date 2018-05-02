package com.forge_CMS.dao;

import com.forge_CMS.bean.Forge_BGO;




public interface Forge_BGO_Dao extends BaseDao<Forge_BGO> {
	Forge_BGO login(String loginName, String password);

}
