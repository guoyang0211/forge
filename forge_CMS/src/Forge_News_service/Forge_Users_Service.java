package Forge_News_service;


import com.forge_CMS.bean.Forge_Users;



public interface Forge_Users_Service extends BaseServise<Forge_Users>  {
	/**
	 * ��¼�ķ���
	 * @param loginName  �û���
	 * @param password   ����
	 * @return   ��¼���û�
	 */
	Forge_Users login(String userName,String password);
	 
}
