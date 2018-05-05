package com.forge.dao_impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.forge.bean.Forge_Product;
import com.forge.bean.Forge_Product_Category;
import com.forge.dao.Forge_Product_Category_Dao;
import com.forge.util.JdbcUtil;
import com.forge.util.ResultSerUtil;

public class Forge_Product_Category_Dao_Impl extends JdbcUtil implements Forge_Product_Category_Dao {

	@Override
	public int add(Forge_Product_Category t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Serializable id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Forge_Product_Category t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Forge_Product_Category> findAll() {
		String sql ="select * from forge_product_category where type=1";
		List<Forge_Product_Category>list=null;
		try {
			rs=getmyExecuteQuery(sql);
			list=ResultSerUtil.findAll(rs, Forge_Product_Category.class);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Forge_Product_Category> findAll2(Serializable id) {
		String sql ="select * from forge_product_category where `type`=2 and parentId="+id;
		List<Forge_Product_Category>list=null;
		try {
			rs=getmyExecuteQuery(sql);
			list=ResultSerUtil.findAll(rs, Forge_Product_Category.class);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Forge_Product_Category findById(Serializable id) {
		String sql="select * from forge_product where id=? ";
		Object[]params={id};
		Forge_Product_Category category=null;
		try {
			rs=getmyExecuteQuery(sql, params);
			category = ResultSerUtil.findById(rs, Forge_Product_Category.class);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return category;
	}

	@Override
	public List<Forge_Product_Category> findAll3() {
		
		String sql ="select * from forge_product_category where type=3";
		List<Forge_Product_Category>list=null;
		try {
			rs=getmyExecuteQuery(sql);
			list=ResultSerUtil.findAll(rs, Forge_Product_Category.class);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Forge_Product> findByT3(Serializable id) {
		String sql="SELECT * FROM `forge_product` WHERE categoryLevel3=?";
		Object [] params={id};
		List<Forge_Product> products=new ArrayList<>();
		try {
			rs=getmyExecuteQuery(sql, params);
			products=ResultSerUtil.findAll(rs, Forge_Product.class);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}


	
	


}
