package Forge_News_service;

import com.forge_CMS.bean.Forge_BGO;






public interface Forge_BGO_Service extends BaseServise<Forge_BGO>  {
	/**
	 * 登录的方法
	 * @param loginName  用户名
	 * @param password   密码
	 * @return   登录的用户
	 */
	Forge_BGO login(String userName,String password);
}
