package com.forge.dao_impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.forge.bean.Forge_Users;
import com.forge.dao.Forge_Users_Dao;
import com.forge.util.JdbcUtil;
import com.forge.util.ResultSerUtil;



/**
 * 用户操作的实现类
 * @author 郭阳
 *
 */
public class Forge_Users_Dao_Impl extends JdbcUtil implements Forge_Users_Dao {

	@Override
	public int add(Forge_Users t) {
		String sql="insert into forge_users(userId,loginName,password,phone,email,address)values(?,?,?,?,?,?)";
		Object[]params={t.getUserId(),t.getLoginName(),t.getPassword(),t.getPhone(),t.getEmail(),t.getAddress()};
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
		String sql="delete from forge_users where id=?";
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
	public int update(Forge_Users t) {
		String sql="update forge_users set loginName=?,password=? where id=?";
		Object[]params={t.getLoginName(),t.getPassword(),t.getUserId()};
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
	public List<Forge_Users> findAll() {
		String sql ="select * from forge_users";
		//创建一个集合来保存所有用户
		List<Forge_Users> users=new ArrayList<>();
	
		try {
			rs=getmyExecuteQuery(sql);
//		
			users=ResultSerUtil.findAll(rs, Forge_Users.class);
			
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


	public Forge_Users login(String loginName, String password) {
		String sql = "select * from forge_users where loginName=? and password=?";
		Object [] params={loginName,password};
		Forge_Users user=null;
		 try {
			
			rs=getmyExecuteQuery(sql, params);

			
			user=ResultSerUtil.findById(rs, Forge_Users.class);
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
	
	/**
	 * 查询用户指定的用户信息
	 */
	@Override
	public Forge_Users findById(Serializable userId) {
		String sql = "select * from forge_users where userId=?";
		Object [] params={userId};
		Forge_Users user=null;
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
			
			user=ResultSerUtil.findById(rs, Forge_Users.class);
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

	@Override
	public Forge_Users findByName(String loginName) {
		
		String sql = "select * from forge_users where loginName=?";
		Object [] params={loginName};
		Forge_Users user=null;
		 try {
			
			rs=getmyExecuteQuery(sql, params);

			user=ResultSerUtil.findById(rs, Forge_Users.class);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return user ;	}


	 }