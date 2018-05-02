
package com.forge.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import Forge_News_service.Forge_Users_Service;
import Forge_News_service_Impl.Forge_Users_Service_Impl;

import com.forge_CMS.bean.Forge_Users;
import com.forge_CMS.dao.Forge_Users_Dao;
import com.forge_CMS.dao_Impl.Forge_Users_Dao_Impl;


@WebServlet("/servlet")
public class Servlet extends HttpServlet {
	Forge_Users_Service userDao= new Forge_Users_Service_Impl();
	

	@Override
	
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("进入了cms");
			resp.setContentType("text/html;charset=UTF-8");
			//瑙ｅ喅璇锋眰缂栫爜
			req.setCharacterEncoding("utf-8");

			//获取out输出
			PrintWriter out =resp.getWriter();
			//获取 用户输入
			String loginName=req.getParameter("loginName");
			String password=req.getParameter("password");
			
			//获取滑块的值 接收过来是0；
			String reslut=req.getParameter("slider_block");
			//接收记住密码
			String remember= req.getParameter("remember");
			//创建用户对象
			Forge_Users user=new Forge_Users();
			//调用登录方法
			user=userDao.login(loginName, password);
			//逻辑判断
			if(user==null){
				JOptionPane.showMessageDialog(null, "用户或密码不正确");
					resp.sendRedirect("login.jsp");
			}else{
				if(reslut.equals("0")){
					JOptionPane.showMessageDialog(null, "请滑动滑块后登录");
					resp.sendRedirect("login.jsp");
				}else{
					if(remember==null){
						resp.sendRedirect("index.jsp");
					}else{
						//登录名保存到cookie会话
						resp.addCookie(new Cookie("loginName","loginName"));
						resp.sendRedirect("index.jsp");
					}
					
				}
			}
	}}

