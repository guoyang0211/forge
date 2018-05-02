package Forge_News_service_Impl;

import java.io.Serializable;
import java.util.List;

import net.spy.memcached.MemcachedClient;

import org.apache.log4j.Logger;




import Forge_News_service.Forge_Users_Service;

import com.forge.util.MemcachedUtil;
import com.forge_CMS.bean.Forge_Users;
import com.forge_CMS.dao.Forge_Users_Dao;
import com.forge_CMS.dao_Impl.Forge_Users_Dao_Impl;



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
		int rowNum = dao.add(t);
		if(rowNum>0){
			logger.info("新增成功");
		}else{
			logger.info("新增失败");
		}
		
	}

	
	public int delete(Serializable id) {
		int rowNum = dao.delete(id);
		if(rowNum>0){
			logger.info("删除成功");
		}else{
			logger.info("删除失败");
		}
		return rowNum;
		
	}


	public boolean update(Serializable id,Forge_Users t) {
		int rowNum = dao.update(id,t);
		if(rowNum>0){
			return true;
		}
		return false;
	
	}

	@Override
	public List<Forge_Users> findAll() {
		
		return dao.findAll();
	}

	@Override
	public Forge_Users findById(Serializable id) {
		Forge_Users user=new Forge_Users();
		//先判断缓存中是否存在对象
		System.out.println("进入了数据库查询");
		user= dao.findById(id);
		
		/*if(client.get("userName")==null){

		
			//放入缓存中
			client.set("userName", 10, user);
			
		}else{
			System.out.println("进入了缓冲区查询");
			user=(Forge_Users) client.get("userName");
		}*/
		return user;
	}


}
