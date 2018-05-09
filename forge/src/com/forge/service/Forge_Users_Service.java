package com.forge.service;

import java.util.List;

import com.forge.bean.Forge_Users;
import com.forge.bean.region;

public interface Forge_Users_Service extends BaseServise<Forge_Users>  {

	Forge_Users findByName(String loginName);

	List<region> findAddress(String parentId);

}
