
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
		System.out.println("������cms");
			resp.setContentType("text/html;charset=UTF-8");
			//解决请求编码
			req.setCharacterEncoding("utf-8");

			//��ȡout���
			PrintWriter out =resp.getWriter();
			//��ȡ �û�����
			String loginName=req.getParameter("loginName");
			String password=req.getParameter("password");
			
			//��ȡ�����ֵ ���չ�����0��
			String reslut=req.getParameter("slider_block");
			//���ռ�ס����
			String remember= req.getParameter("remember");
			//�����û�����
			Forge_Users user=new Forge_Users();
			//���õ�¼����
			user=userDao.login(loginName, password);
			//�߼��ж�
			if(user==null){
				JOptionPane.showMessageDialog(null, "�û������벻��ȷ");
					resp.sendRedirect("login.jsp");
			}else{
				if(reslut.equals("0")){
					JOptionPane.showMessageDialog(null, "�뻬��������¼");
					resp.sendRedirect("login.jsp");
				}else{
					if(remember==null){
						resp.sendRedirect("index.jsp");
					}else{
						//��¼�����浽cookie�Ự
						resp.addCookie(new Cookie("loginName","loginName"));
						resp.sendRedirect("index.jsp");
					}
					
				}
			}
	}}

