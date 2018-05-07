
package com.forge.dao_impl;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.forge.bean.Forge_Cart;
import com.forge.dao.Forge_CartDao;
import com.forge.util.JdbcUtil;
import com.forge.util.ResultSerUtil;


public class Forge_CartDaoImpl extends JdbcUtil implements Forge_CartDao {

	@Override
	public int add(Forge_Cart t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Serializable id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Forge_Cart t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Forge_Cart> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Forge_Cart findById(Serializable id) {
		
		return null;
	}

	@Override
	public List<Forge_Cart> findByUserId(Serializable id) {
		String sql = "select * from forge_cart where userId = ?";
		Object []param = {id};
		List<Forge_Cart> cartList = new ArrayList();
		try {
			rs = getmyExecuteQuery(sql, param);
			cartList = ResultSerUtil.findAll(rs, Forge_Cart.class);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cartList;
	}

}
