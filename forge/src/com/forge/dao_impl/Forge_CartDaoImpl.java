
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

	
	public void add(Serializable userId,Serializable productId,int num,double price) {
		String sql="INSERT INTO forge_cart (userId,productId,productNum,price) VALUES(?,?,?,?) ";
		Object [] params={userId,productId,num,price};
		int rowNum=0;
		try {
			rowNum=getmyExecuteUpdate(sql, params);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int delete(Serializable id) {
		
		return 0;
	}


	public int update(int num,double price,Serializable userId,Serializable productId) {
		String sql="UPDATE forge_cart SET productNum=?,price=?  WHERE userId=? AND productId=?";
		Object[]params={num,price,userId,productId};
		int rowNum=0;
		try {
			rowNum=getmyExecuteUpdate(sql, params);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowNum;
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
		List<Forge_Cart> cartList = null;;
		try {
			rs = getmyExecuteQuery(sql, param);
			cartList = ResultSerUtil.findAll(rs, Forge_Cart.class);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return cartList;
	}

	@Override
	public List<Forge_Cart> findAll(Serializable id) {
		String sql="SELECT * FROM forge_cart WHERE userId=?";
		List<Forge_Cart>lists=new ArrayList();
		
		try {
			rs=getmyExecuteQuery(sql, id);
			lists=ResultSerUtil.findAll(rs, Forge_Cart.class);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lists;
	}

	@Override
	public int update(Forge_Cart t) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int add(Forge_Cart t) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
