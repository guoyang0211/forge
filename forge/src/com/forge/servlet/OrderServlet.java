package com.forge.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.forge.bean.Cart;
import com.forge.bean.CartItem;
import com.forge.bean.Forge_Cart;
import com.forge.bean.Forge_Order;
import com.forge.bean.Forge_Order_Detail;
import com.forge.bean.Forge_Users;
import com.forge.dao.Forge_Order_Detail_Dao;
import com.forge.dao_impl.Forge_Order_Detail_Dao_Impl;
import com.forge.service.Forge_CartService;
import com.forge.service.Forge_Order_Service;
import com.forge.service_impl.Forge_CartServiceImpl;
import com.forge.service_impl.Forge_Order_Service_Impl;
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	Forge_Order_Service service =new Forge_Order_Service_Impl();
	Forge_CartService cartService=new Forge_CartServiceImpl();
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
			System.out.println("jsp页面过来的method"+method);
			switch(method){
			case"findAll":
				findById(req,resp);
				break;
			case"add":
				add(req,resp);
				break;
			
			}
			
		
	}
	
	
	

	
	

	
	
	/**
	 * 把订单添加 到数据库order
	 * @param req
	 * @param resp
	 */
	private void add(HttpServletRequest req, HttpServletResponse resp) {
		//获取购物车里的商品
		Cart cart= (Cart) req.getSession().getAttribute("cart");
		System.out.println("集合的长度是"+cart.getMap().size());
		//获取下单的数量
		//获取下单商品的Id
		Forge_Users user=(Forge_Users) req.getSession().getAttribute("forgeUser");
		String num2="";//记录订单编号的转换
		//获取总价钱
		String cost2 = req.getParameter("cost");
		Double cost = Double.valueOf(cost2);
		//产生随机数(订单编号)
		 int random=(int)(Math.random()*123456789);
		 String num = String.valueOf(random);
		 System.out.println("==============>"+num);
		 //遍历购物车里的map
		 Iterator a=cart.getMap().entrySet().iterator();
		 while(a.hasNext()){
			Map.Entry entry=(Entry) a.next();
			String key=entry.getKey().toString();
			CartItem item=(CartItem) entry.getValue();
			System.out.println("即合理获取的Id"+item.getProduct().getId());
			//获取数据库的订单编号
			Forge_Order order = service.findAll(user.getUserId());
			System.out.println("user.getUserId()"+user.getUserId());
			System.out.println(order);
			//如果生成的订单号与数据库的定好号重复，则重新生成
			 if(num.equals(order.getSerialNumber())){
				 int random2=(int)(Math.random()*123456789);
				 num2 = Integer.toString(random); 
				 System.out.println("订单编号是"+num2);
				 //添加到order
				service.add(user.getUserId(),user.getLoginName(),user.getAddress(),item.getPrice(),num2);
				//成功下单后把数据库里的购物车对应的商品删除
				cartService.delete(Integer.toString(user.getUserId()),item.getProduct().getId());
				//添加到detail
				addDetail(order.getId(),Integer.valueOf(item.getProduct().getId()),item.getNum(),item.getPrice());
			 }else{
				 service.add(user.getUserId(),user.getLoginName(),user.getAddress(),item.getPrice(),num);
				 cartService.delete(Integer.toString(user.getUserId()),item.getProduct().getId());
				 addDetail(order.getId(),Integer.valueOf(item.getProduct().getId()),item.getNum(),item.getPrice());
			 }
			
		 }
		 
		
		
		
		
	}
	/**
	 * 根据orderId，商品Id，添加到后台   数量，总价钱
	 * @param req
	 * @param resp
	 */
	private void addDetail(int orderId, int productId, int num, double price) {
		System.out.println("进入到addDetail");
		Forge_Order_Detail_Dao detail =new Forge_Order_Detail_Dao_Impl();
		detail.add(orderId,productId,num,price);
		
	}

	/*
	 * 根据用户的ID查询自己的订单(只有登录后在可以查询)
	 */
	private void findById(HttpServletRequest req, HttpServletResponse resp) {
		Forge_Users user=(Forge_Users) req.getSession().getAttribute("forgeUser");
		Forge_Order order = service.findAll(user.getUserId());
	}

}
