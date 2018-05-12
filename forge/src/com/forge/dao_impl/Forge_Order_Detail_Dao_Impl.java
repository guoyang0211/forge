package com.forge.dao_impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import com.forge.bean.Forge_Order_Detail;
import com.forge.dao.Forge_Order_Detail_Dao;
import com.forge.util.JdbcUtil;

public class Forge_Order_Detail_Dao_Impl extends JdbcUtil implements Forge_Order_Detail_Dao{

	@Override
	public int add(Forge_Order_Detail t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Serializable id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Forge_Order_Detail t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Forge_Order_Detail> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Forge_Order_Detail findById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int orderId, int productId, int num, double price) {
		String sql="INSERT INTO forge_order_detail (orderId,productId,quantity,cost) VALUES (?,?,?,?)";
		Object[]params={orderId,productId,num,price};
		try {
			getmyExecuteUpdate(sql, params);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
