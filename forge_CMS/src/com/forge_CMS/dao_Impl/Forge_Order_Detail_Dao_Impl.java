package com.forge_CMS.dao_Impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.forge.util.JdbcUtil;
import com.forge.util.ResultSerUtil;
import com.forge_CMS.bean.Forge_News;
import com.forge_CMS.bean.Forge_Order_Detail;
import com.forge_CMS.dao.Forge_Order_Detail_Dao;
/**
 * Forge_Order_Detail_Dao_Impl类
 * @author 郭阳
 *
 */
public class Forge_Order_Detail_Dao_Impl extends JdbcUtil implements  Forge_Order_Detail_Dao{

	@Override
	public int add(Forge_Order_Detail t) {
		String sql="insert into forge_order_detail()values()";
		Object[]params={};
		//影响的行数
		int rowNum=0;
		try {
			rowNum=getmyExecuteUpdate(sql, params);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowNum;
	}

	@Override
	public int delete(Serializable id) {
		String sql="delete from forge_order_detail where id=? ";
		Object[]params={id};
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
	public int update(Serializable id,Forge_Order_Detail t) {
		
		String sql="update forge_order_detail set quantity=? where id=?";
		Object[]params={t.getQuantity(),id};
		
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
		return rowNum ;
	}

	

	public Forge_News login(String loginName, String password) {
		String sql = "select * forge_order_detail where loginName=? and password=?";
					
		Object [] params={loginName,password};
		Forge_News news=null;
		 try {
			
			rs=getmyExecuteQuery(sql, params);

			
			news=ResultSerUtil.findById(rs, Forge_News.class);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return news ;
	}
	
	/**
	 * 查询用户指定的用户信息
	 */
	@Override
	public Forge_Order_Detail findById(Serializable userId) {
		String sql = "select * from forge_order_detail where id=?";
		Object [] params={userId};
		Forge_Order_Detail detail=null;
		 try {
			
			rs=getmyExecuteQuery(sql, params);

			detail=ResultSerUtil.findById(rs, Forge_Order_Detail.class);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return detail ;

	}

	@Override
	public List<Forge_Order_Detail> findAll() {
		String sql="select * from forge_order_detail";
		//创建一个集合保存所有信息
		List<Forge_Order_Detail>detail=new ArrayList<>();
		try {
			rs=getmyExecuteQuery(sql);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		detail=ResultSerUtil.findAll(rs, Forge_Order_Detail.class);
		return detail;
	}
}
