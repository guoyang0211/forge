package com.forge.dao;

import com.forge.bean.Forge_Order;

public interface Forge_Order_Dao extends BaseDao<Forge_Order> {
	Forge_Order findAll(int userId);

	void add(int userId, String loginName, String address, Double cost,
			String num2);
}
