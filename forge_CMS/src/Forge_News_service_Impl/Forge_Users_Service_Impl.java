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
	//����ʵ��
	Forge_Users_Dao dao=new Forge_Users_Dao_Impl();
	Logger logger=Logger.getLogger(Forge_Users_Service_Impl.class);
	
	public Forge_Users login(String loginName, String password) {
		
		Forge_Users user = dao.login(loginName, password);
		 if(user==null){
			 logger.info("��¼�ɹ�");
		 }else{
			 logger.info("��¼ʧ��");
		 }
		return user;
	}

	
	public void add(Forge_Users t) {
		int rowNum = dao.add(t);
		if(rowNum>0){
			logger.info("�����ɹ�");
		}else{
			logger.info("����ʧ��");
		}
		
	}

	
	public int delete(Serializable id) {
		int rowNum = dao.delete(id);
		if(rowNum>0){
			logger.info("ɾ���ɹ�");
		}else{
			logger.info("ɾ��ʧ��");
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
		//���жϻ������Ƿ���ڶ���
		System.out.println("���������ݿ��ѯ");
		user= dao.findById(id);
		
		/*if(client.get("userName")==null){

		
			//���뻺����
			client.set("userName", 10, user);
			
		}else{
			System.out.println("�����˻�������ѯ");
			user=(Forge_Users) client.get("userName");
		}*/
		return user;
	}


}
