package com.forge.dao_impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import com.forge.bean.Forge_Order;
import com.forge.dao.Forge_Order_Dao;
import com.forge.util.JdbcUtil;
import com.forge.util.ResultSerUtil;

public class Forge_Order_Dao_Impl extends JdbcUtil implements Forge_Order_Dao {

	@Override
	public int add(Forge_Order t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Serializable id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Forge_Order t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Forge_Order> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Forge_Order findById(Serializable id) {
		
		return null;
	}

	@Override
	public Forge_Order findAll(int userId) {
		Forge_Order oredr=null;
		String sql="SELECT * FROM forge_order WHERE userId=?";
		try {
			rs=getmyExecuteQuery(sql, userId);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oredr=ResultSerUtil.findById(rs, Forge_Order.class);
		
		return oredr;
	}

	@Override
	public void add(int userId, String loginName, String address, Double cost,
			String num2) {
		String sql="INSERT INTO forge_order (userId,loginName,userAddress,cost,serialNumber) VALUES (?,?,?,?,?)";
		Object[]params={userId,loginName,address,cost,num2};
		
		try {
			getmyExecuteUpdate(sql, params);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}
