package Forge_News_service;

import com.forge_CMS.bean.Forge_BGO;






public interface Forge_BGO_Service extends BaseServise<Forge_BGO>  {
	/**
	 * ��¼�ķ���
	 * @param loginName  �û���
	 * @param password   ����
	 * @return   ��¼���û�
	 */
	Forge_BGO login(String userName,String password);
}
