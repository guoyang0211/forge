package Forge_News_service;


import com.forge_CMS.bean.Forge_Users;



public interface Forge_Users_Service extends BaseServise<Forge_Users>  {
	/**
	 * 登录的方法
	 * @param loginName  用户名
	 * @param password   密码
	 * @return   登录的用户
	 */
	Forge_Users login(String userName,String password);
	 
}
