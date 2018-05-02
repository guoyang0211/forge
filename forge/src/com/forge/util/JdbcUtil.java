package com.forge.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



public class JdbcUtil {
	//提取公共的jdbc API
	static Connection conn=null;
	static PreparedStatement statement=null;
	protected static ResultSet rs=null;
	/**
	 * 提取公共的开启连接
	 * @return
	 * @throws ClassNotFoundException
	 * 
	 * 
	 * public static boolean getConnection() throws ClassNotFoundException{	
		//加载驱动 (反射技术)
			try {
				Class.forName(ConfigManager.getInstance().getValue("jdbc.driver"));
				conn=DriverManager.getConnection(ConfigManager.getInstance().getValue("jdbc.url"),
						ConfigManager.getInstance().getValue("jdbc.user"),
						ConfigManager.getInstance().getValue("jdbc.password"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		
		return true;
	}
	 * 
	 * 
	 */
	
	/**
	 * 使用数据源连接数据库
	 * 配置taomcat数据源
	 * Spring 整合数据源  ali  c3p0   dbcp
	 */
	public static boolean getConnection(){
		
		try {//初始上下文对象tomcat容器
			Context con=new InitialContext();
			//通过数据源 中的name属性获取指定的数据源
			DataSource ds=(DataSource) con.lookup("java:comp/env/jdbc/forge");
			//从连接池中获取连接connection pool
			try {
				conn=ds.getConnection();
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
		
	}
	
	
	
	
	
	
	/**
	 * 创建公共的关闭流
	 */
	public static void closeConnection() {
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(statement!=null){
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 共同的sql语句 （增，删，改）
	 * @param sql
	 * @param param
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int getmyExecuteUpdate(String sql,Object...param) throws ClassNotFoundException, SQLException{
		int rowNumb=0;//影响的行数
		if(getConnection()){//连接接通执行
			statement=conn.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				statement.setObject(i+1,param[i]);
			}
			//执行
			rowNumb=statement.executeUpdate();
		}
	closeConnection();
		return rowNumb;
		
	}
	/**
	 * 共同的sql查语句
	 * @param sql
	 * @param param
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static ResultSet getmyExecuteQuery(String sql,Object...param) throws ClassNotFoundException, SQLException{
		
		if(getConnection()){//连接接通执行
			statement=conn.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				statement.setObject(i+1,param[i]);
			}
			//执行
			rs=statement.executeQuery();
		}
		return rs;
		
		
	}
	
	

}