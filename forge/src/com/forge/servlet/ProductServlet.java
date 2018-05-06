package com.forge.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.forge.bean.Forge_Product;
import com.forge.service.Forge_Product_Service;
import com.forge.service_impl.Forge_Product_Service_Impl;
@WebServlet("/buyServlet")
public class ProductServlet extends HttpServlet{
//实例化service层对象
	Forge_Product_Service service=new Forge_Product_Service_Impl();
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
		System.out.println("jsp页面或得的method===》"+method);
		if(method==null){
			List<Forge_Product>product=service.findAll();
			req.setAttribute("product", product);
			//转发
			req.getRequestDispatcher("my-car.jsp");
		}else{
			switch(method){
			case "add":
				addCart(req,resp);
				break;
			case "findCart":
				findCart(req,resp);
				break;
			case "del":
				delCart(req,resp);
				break;
			case "clear":
				clearCart(req,resp);
				break;
			}
		}
		
		
	}

	private void clearCart(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}

	private void delCart(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}
/**
 * 
 * @param req
 * @param resp
 */
	private void findCart(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}
/**
 * 把商品加入购物车
 * @param req
 * @param resp
 */
	private void addCart(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("method进来的addCart");
		//获取商品的数量
		String productId = req.getParameter("id");
		int id = Integer.valueOf(productId);
		System.out.println("productId====>"+id);
		
	}

}
