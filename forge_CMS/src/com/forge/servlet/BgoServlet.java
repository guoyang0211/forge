package com.forge.servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import Forge_News_service.Forge_BGO_Service;
import Forge_News_service_Impl.Forge_BGO_Service_Impl;

import com.forge_CMS.bean.Forge_BGO;
import com.forge_CMS.dao.Forge_BGO_Dao;
import com.forge_CMS.dao_Impl.Forge_BGO_Dao_Impl;









@WebServlet("/BgoServlet")
public class BgoServlet extends HttpServlet {
	
	Forge_BGO_Service service= new Forge_BGO_Service_Impl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String method = req.getParameter("method");
		System.out.println(method);
		switch(method){
		case "login":
			bgoLogin(req,resp);//调用登录方法
		}
		
	}
	
	private void bgoLogin(HttpServletRequest req, HttpServletResponse resp) {
			resp.setCharacterEncoding("utf-8");
		//获取用户输入的用户名
		String userName = req.getParameter("userName");
		System.out.println(userName);
		//获取用户输入的密码
		String password = req.getParameter("password");
		System.out.println(password);
		
		
			//创建实体类bgo对象
			Forge_BGO bgo=new Forge_BGO();

			bgo= service.login(userName, password);
			
			if(bgo==null){
				JOptionPane.showMessageDialog(null, "用户或密码不正确");
				try {
					resp.sendRedirect("production/login.jsp");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				System.out.println("成功");
				try {
					resp.sendRedirect("production/index.jsp");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		
		
	}

}
