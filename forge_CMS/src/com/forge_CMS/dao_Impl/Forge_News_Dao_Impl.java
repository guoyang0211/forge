package com.forge_CMS.dao_Impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.forge.util.JdbcUtil;
import com.forge.util.ResultSerUtil;
import com.forge_CMS.bean.Forge_News;
import com.forge_CMS.bean.Forge_Users;
import com.forge_CMS.dao.Forge_News_Dao;

public class Forge_News_Dao_Impl extends JdbcUtil implements Forge_News_Dao {

	@Override
	public int add(Forge_News t) {
		String sql="insert into forge_news(title,content,createTime,img)values(?,?,?,?)";
		Object[]params={t.getTitle(),t.getContent(),t.getCreateTime(),t.getImg()};
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
		String sql="delete from forge_news where id=? ";
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
	public int update(Serializable id,Forge_News t) {
		String sql="update from forge_news set title=?,content=?, where id=? ";
		Object[]params={t.getTitle(),t.getContent()};
		
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
		String sql = "select * from forge_users where loginName=? and password=?";
					
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
	public Forge_News findById(Serializable userId) {
		String sql = "select * from forge_news where id=?";
		Object [] params={userId};
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

	@Override
	public List<Forge_News> findAll() {
		String sql="select * from forge_news";
		//创建一个集合保存所有信息
		List<Forge_News>newss=new ArrayList<>();
		try {
			rs=getmyExecuteQuery(sql);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newss=ResultSerUtil.findAll(rs, Forge_News.class);
		return newss;
	}


}
