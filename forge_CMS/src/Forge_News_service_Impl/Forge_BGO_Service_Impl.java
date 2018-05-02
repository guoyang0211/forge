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
	//����ʵ��
	Forge_BGO_Dao dao=new Forge_BGO_Dao_Impl();
	Logger logger=Logger.getLogger(Forge_BGO_Service_Impl.class);
	
	public Forge_BGO login(String loginName, String password) {
		
		Forge_BGO bgo = dao.login(loginName, password);
		 if(bgo!=null){
			 logger.info("��¼�ɹ�");
		 }else{
			 logger.info("��¼ʧ��");
		 }
		return bgo;
	}

	
	public void add(Forge_BGO t) {
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
		//���жϻ������Ƿ���ڶ���
		if(client.get("userName")==null){
			System.out.println("���������ݿ��ѯ");
			bgo= dao.findById(id);
			//���뻺����
			client.set("userName", 10, bgo);
			
		}else{
			System.out.println("�����˻�������ѯ");
			bgo=(Forge_BGO) client.get("userName");
		}
		return bgo;
	}


}
