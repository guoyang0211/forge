package Forge_News_service_Impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.forge_CMS.bean.Forge_News;
import com.forge_CMS.bean.Forge_Order_Detail;
import com.forge_CMS.dao.Forge_News_Dao;
import com.forge_CMS.dao.Forge_Order_Detail_Dao;
import com.forge_CMS.dao_Impl.Forge_News_Dao_Impl;
import com.forge_CMS.dao_Impl.Forge_Order_Detail_Dao_Impl;

import Forge_News_service.Forge_Order_Detail_Service;

public class Forge_Order_Detail_Service_Impl implements Forge_Order_Detail_Service{

	//����ʵ��
	Forge_Order_Detail_Dao dao=new Forge_Order_Detail_Dao_Impl();
			//����Logger����
			Logger logger=Logger.getLogger(Forge_News_Service_Impl.class);
			@Override
			public void add(Forge_Order_Detail t) {
				int rowNum = dao.add(t);
				if(rowNum>0){
					logger.info("�����ɹ�");
				}else{
					logger.info("����ʧ��");
				}
				
			}

			public Forge_News_Dao_Impl login(String loginName, String password) {
				
				Forge_News_Dao_Impl news =null;
				 if(news==null){
					 logger.info("��¼�ɹ�");
				 }else{
					 logger.info("��¼ʧ��");
				 }
				return news;
			}
			@Override
			public int delete(Serializable id) {
				int rowNum = dao.delete(id);
				if(rowNum>0){
					logger.info("ɾ���ɹ�");
				}else{
					logger.info("ɾ��ʧ��");
				}
				return rowNum;
				
			}

			@Override
			public boolean update(Serializable id,Forge_Order_Detail t) {
				int rowNum = dao.update(id, t);
				if(rowNum>0){
					return true;
				}
				return false;
				
			}

			@Override
			public List<Forge_Order_Detail> findAll() {
				
				return dao.findAll();
			}

			@Override
			public Forge_Order_Detail findById(Serializable id) {
				
				return dao.findById(id);
			}

}
