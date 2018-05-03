package com.forge.service;

import com.forge.bean.Forge_Users;

public interface Forge_Users_Service extends BaseServise<Forge_Users>  {

	Forge_Users findByName(String loginName);

}
