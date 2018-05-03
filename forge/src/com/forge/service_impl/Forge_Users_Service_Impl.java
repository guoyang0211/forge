package com.forge.service_impl;

import java.io.Serializable;
import java.util.List;

import net.spy.memcached.MemcachedClient;

import org.apache.log4j.Logger;

import com.forge.bean.Forge_Users;
import com.forge.dao.Forge_Users_Dao;
import com.forge.dao_impl.Forge_Users_Dao_Impl;
import com.forge.service.Forge_Users_Service;
import com.forge.util.MemcachedUtil;



/**
 * 
 * @author 郭阳
 *
 */

public class Forge_Users_Service_Impl implements Forge_Users_Service {
	MemcachedClient client = MemcachedUtil.getInstance();
	//创建实例
	Forge_Users_Dao dao=new Forge_Users_Dao_Impl();
	Logger logger=Logger.getLogger(Forge_Users_Service_Impl.class);
	
	public Forge_Users login(String loginName, String password) {
		
		Forge_Users user = dao.login(loginName, password);
		 if(user==null){
			 logger.info("登录成功");
		 }else{
			 logger.info("登录失败");
		 }
		return user;
	}

	
	public void add(Forge_Users t) {
		
		System.err.println("================================================="+t);
		int rowNum = dao.add(t);
		
		System.err.println("================================================="+t);
		if(rowNum>0){
			logger.info("新增成功");
		}else{
			logger.info("新增失败");
		}
		
	}

	
	public void delete(Serializable id) {
		int rowNum = dao.delete(id);
		if(rowNum>0){
			logger.info("删除成功");
		}else{
			logger.info("删除失败");
		}
		
	}


	public void update(Forge_Users t) {
		int rowNum = dao.update(t);
		if(rowNum>0){
			logger.info("修改成功");
		}else{
			logger.info("修改失败");
		}
	
	}

	@Override
	public List<Forge_Users> findAll() {
		
		return dao.findAll();
	}

	@Override
	public Forge_Users findById(Serializable id) {
		Forge_Users user=new Forge_Users();
		//先判断缓存中是否存在对象
		if(client.get("userName")==null){
			System.out.println("进入了数据库查询");
			user= dao.findById(id);
			//放入缓存中
			client.set("userName", 10, user);
			
		}else{
			System.out.println("进入了缓冲区查询");
			user=(Forge_Users) client.get("userName");
		}
		return user;
	}


	@Override
	public Forge_Users findByName(String loginName) {
		Forge_Users user = null;
		user = dao.findByName(loginName);
		return user;
	}


}
