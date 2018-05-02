package Forge_News_service;

import com.forge_CMS.bean.Forge_News;




public interface Forge_News_Service extends BaseServise<Forge_News> {
	/**
	 * 登录的方法
	 * @param loginName  用户名
	 * @param password   密码
	 * @return   登录的用户
	 */
	Forge_News login(String userName,String password);
}
