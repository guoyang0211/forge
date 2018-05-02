package Forge_News_service_Impl;

import java.io.Serializable;
import java.util.List;

import net.spy.memcached.MemcachedClient;

import org.apache.log4j.Logger;




import Forge_News_service.Forge_BGO_Service;
import Forge_News_service.Forge_Users_Service;

import com.forge.util.MemcachedUtil;
import com.forge_CMS.bean.Forge_BGO;
import com.forge_CMS.dao.Forge_BGO_Dao;
import com.forge_CMS.dao_Impl.Forge_BGO_Dao_Impl;




public class Forge_BGO_Service_Impl implements Forge_BGO_Service {
	MemcachedClient client = MemcachedUtil.getInstance();
	//创建实例
	Forge_BGO_Dao dao=new Forge_BGO_Dao_Impl();
	Logger logger=Logger.getLogger(Forge_BGO_Service_Impl.class);
	
	public Forge_BGO login(String loginName, String password) {
		
		Forge_BGO bgo = dao.login(loginName, password);
		 if(bgo!=null){
			 logger.info("登录成功");
		 }else{
			 logger.info("登录失败");
		 }
		return bgo;
	}

	
	public void add(Forge_BGO t) {
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


	public boolean update(Serializable id,Forge_BGO t) {
		int rowNum = dao.update(id,t);
		if(rowNum>0){
			return true;
		}
		return false;
	
	}

	@Override
	public List<Forge_BGO> findAll() {
		
		return dao.findAll();
	}

	@Override
	public Forge_BGO findById(Serializable id) {
		Forge_BGO bgo=new Forge_BGO();
		//先判断缓存中是否存在对象
		if(client.get("userName")==null){
			System.out.println("进入了数据库查询");
			bgo= dao.findById(id);
			//放入缓存中
			client.set("userName", 10, bgo);
			
		}else{
			System.out.println("进入了缓冲区查询");
			bgo=(Forge_BGO) client.get("userName");
		}
		return bgo;
	}


}
