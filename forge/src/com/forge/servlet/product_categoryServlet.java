package com.forge.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.forge.bean.Forge_Product_Category;
import com.forge.dao.Forge_Product_Category_Dao;
import com.forge.service.Forge_Product_Category_Service;
import com.forge.service_impl.Forge_Product_Category_Service_Impl;
@WebServlet("/categoryServlet")
public class product_categoryServlet extends HttpServlet {
	Forge_Product_Category_Service service=new Forge_Product_Category_Service_Impl();

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
		System.out.println("jspҳ�������method====��"+method);
		switch (method) {
		case "findAll":
			findAll(req,resp);
			break;

		default:
			break;
		}
	}

	private void findAll(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("������findAll");
		List<Forge_Product_Category> list = service.findAll();
		//�Ѽ��ϴ��session������
		req.getSession().setAttribute("list", list);
		try {
			resp.sendRedirect("index.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		PrintWriter writer;
//		try {
//			writer = resp.getWriter();
//			writer.print("true");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}