package Forge_News_service;

import com.forge_CMS.bean.Forge_News;




public interface Forge_News_Service extends BaseServise<Forge_News> {
	/**
	 * ��¼�ķ���
	 * @param loginName  �û���
	 * @param password   ����
	 * @return   ��¼���û�
	 */
	Forge_News login(String userName,String password);
}
