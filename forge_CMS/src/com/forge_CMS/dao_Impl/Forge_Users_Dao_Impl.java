package com.forge_CMS.dao_Impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.forge.util.JdbcUtil;
import com.forge.util.ResultSerUtil;
import com.forge_CMS.bean.Forge_Users;
import com.forge_CMS.dao.Forge_Users_Dao;



/**
 * �û�������ʵ����
 * @author ����
 *
 */
public class Forge_Users_Dao_Impl extends JdbcUtil implements Forge_Users_Dao {

	@Override
	public int add(Forge_Users t) {
		String sql="insert into forge_users(id,loginName,userName,password,phone,email,address)values(?,?,?,?,?,?,?)";
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
	 * ��Ϊ�û����ܸ���id��������ѡ��ɾ�������ǲ�ȷ��������
	 * �Ѳ�������ΪSerializable
	 */
	@Override
	public int delete(Serializable id) {
		String sql="delete from forge_users where userId=?";
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
	 * �޸�
	 */
	@Override
	public int update(Serializable id,Forge_Users t) {
		String sql="update forge_users set loginName=?,phone=?,address=?,email=? where userId=?";
		Object[]params={t.getLoginName(),t.getPhone(),t.getAddress(),t.getEmail(),id};
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


	public List<Forge_Users> findAll() {
		String sql ="select * from forge_users";
		//����һ�����������������û�
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
	 * ��ѯ�û�ָ�����û���Ϣ
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

	



	 }