package com.forge_CMS.dao_Impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.forge.util.JdbcUtil;
import com.forge.util.ResultSerUtil;
import com.forge_CMS.bean.Forge_BGO;
import com.forge_CMS.dao.Forge_BGO_Dao;




/**
 * 用户操作的实现类
 * @author 郭阳
 *
 */
public class Forge_BGO_Dao_Impl extends JdbcUtil implements Forge_BGO_Dao {

	@Override
	public int add(Forge_BGO t) {
		String sql="insert into forge_bgo(id,loginName,password)values(?,?,?)";
		Object[]params={};
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

	/**
	 * 
	 * 因为用户可能根据id或者其他选择删除，我们不确定，所以
	 * 把参数设置为Serializable
	 */
	@Override
	public int delete(Serializable id) {
		String sql="delete from forge_bgo where id=?";
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

	/**
	 * 修改
	 */
	@Override
	public int update(Serializable id,Forge_BGO t) {
		String sql="update forge_bgo set loginName=?,password=? where id=?";
		Object[]params={t.getUserName(),t.getPassword(),t.getId()};
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


	public List<Forge_BGO> findAll() {
		String sql ="select * from forge_bgo";
		//创建一个集合来保存所有用户
		List<Forge_BGO> users=new ArrayList<>();
	
		try {
			rs=getmyExecuteQuery(sql);
//		
			users=ResultSerUtil.findAll(rs, Forge_BGO.class);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return users;
	}


	public Forge_BGO login(String loginName, String password) {
		String sql = "select * from forge_bgo where loginName=? and password=?";
		Object [] params={loginName,password};
		Forge_BGO bgo=null;
		 try {
			
			rs=getmyExecuteQuery(sql, params);

			
			bgo=ResultSerUtil.findById(rs, Forge_BGO.class);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return bgo ;
	}
	
	/**
	 * 查询用户指定的用户信息
	 */
	@Override
	public Forge_BGO findById(Serializable userId) {
		String sql = "select * from forge_bgo where bgoId=?";
		Object [] params={userId};
		Forge_BGO user=null;
		 try {
			
			rs=getmyExecuteQuery(sql, params);
//			if(rs.next()){
//				int id=rs.getInt("id");
//				String userName=rs.getString("userName");
//				String name=rs.getString("loginName");
//				String pwd=rs.getString("password");
//				String identityCode=rs.getString("identityCode");
//				String email=rs.getString("email");
//				String mobile=rs.getString("mobile");
//				int sex=rs.getInt("sex");
//				int type=rs.getInt("type");
//				user=new User(id, userName, name, pwd, sex, identityCode, email, mobile, type);
//			}
			
			user=ResultSerUtil.findById(rs, Forge_BGO.class);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return user ;

	}


	 }