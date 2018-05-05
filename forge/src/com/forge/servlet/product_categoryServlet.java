package com.forge.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.forge.bean.Forge_Product;
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
		System.out.println("jsp页面进来的method====》"+method);
		switch (method) {
		case "findAll":
			findAll(req,resp);
			break;
		case "findAll2":
			findAll2(req,resp);
			break;
		case "findAll3":
			findAll3(req,resp);
			break;
		case "findByT3":
			findByT3(req,resp);
			break;
		case "pageInfo":
			pageInfo(req,resp);
			break;

		default:
			break;
		}
	}
private void pageInfo(HttpServletRequest req, HttpServletResponse resp) {
	String id = req.getParameter("id");

	req.getSession().setAttribute("pageid", id);
				try {
					resp.sendRedirect("page.jsp");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		
	}

/**
 * 根据三级菜单的id获取下级商品
 * @param req
 * @param resp
 */
	private void findByT3(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("根据三级菜单的id获取下级商品");
		String id = req.getParameter("id");
		List<Forge_Product> products =service.findByT3(id);
		req.getSession().setAttribute("products", products);
		req.getSession().setAttribute("id", id);
		try {
			resp.sendRedirect("my-all.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/**
 * 查询三级菜单方法
 * @param req
 * @param resp
 */
	private void findAll3(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("进入了findAll3");
		List<Forge_Product_Category> findAll3 = service.findAll3();
		//把type3的存进session  用el表达式获取
		req.getSession().setAttribute("findAll3", findAll3);
		
	}

	/**
	 * 二级菜单
	 * @param req
	 * @param resp
	 */
	private void findAll2(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("进入了findAll2");
		List<Forge_Product_Category> list1 =  (List<Forge_Product_Category>) req.getSession().getAttribute("list");
		for (int i = 0; i < list1.size(); i++) {
			int id = list1.get(i).getId();
			List<Forge_Product_Category> list= service.findAll2(id);
			switch(id){
				case 548 :
					req.getSession().setAttribute("type21", list);
					System.out.println("================================="+list);
				break;
				case 628 :
					req.getSession().setAttribute("type22", list);
				break;
				case 660 :
					req.getSession().setAttribute("type23", list);
				break;
				case 670 :
					req.getSession().setAttribute("type24", list);
				break;
				case 676 :
					req.getSession().setAttribute("type25", list);
				break;
				case 681 :
					req.getSession().setAttribute("type26", list);
				break;
		}
		}
	
		
	}

	/**
	 * 一级菜单
	 */
	private void findAll(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("进入了findAll");
		List<Forge_Product_Category> list = service.findAll();
		
		req.getSession().setAttribute("list", list);
		
		PrintWriter writer;
		try {
			writer = resp.getWriter();
			writer.print("true");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//直接加载二级菜单
		findAll2(req,resp);
		//直接加载三级菜单
		findAll3(req,resp);
	}
	
	
	
	
	
	
	
	
	

}
