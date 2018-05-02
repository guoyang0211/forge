package com.forge.util;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 1.我们从数据库中获取的结果集就是resultSet
 * 2.结果集中的类型我们不确定
 * 3.sql语句执行就决定T的类型了
 * 4.通过反射机制向实体类中的属性赋值
 * @author 郭阳
 *
 */
public class ResultSerUtil {
	
	/***
	 * 01.务必需要一个结果集ResultSet,才能获取T类型
	 * 02.反射务必需要Class<T>
	 * 
	 */
	public static <T> T findById(ResultSet rs,Class<T> c){
		T object=null;
		try {
			if(rs.next()){
				try {
					object=c.newInstance();//实例化对象
				Field[] fields = c.getDeclaredFields();//获取实体类所有属性（包括）
				for (Field field : fields) {
					field.setAccessible(true);//打开权限
					field.set(object,rs.getObject(field.getName()));
				}
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
	
	
	public static <T> List<T>findAll(ResultSet rs,Class<T>c){
		T object=null;
		List<T> list=new ArrayList();//创建集合   保存每一个对象
		try {
			
			try {
				while(rs.next()){
					object=c.newInstance();//通过反射实例化对象
					Field[]fields  = c.getDeclaredFields();//获取所有属性
					for (Field field2 : fields) {
						field2.setAccessible(true);//打开权限
						field2.set(object,rs.getObject(field2.getName()));
					}
					//将对象加入到集合
					list.add(object);
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		
		
		return list;
	}
	
}
