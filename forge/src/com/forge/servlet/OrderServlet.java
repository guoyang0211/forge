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
			System.out.println("jspҳ�������method"+method);
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
	 * �Ѷ������ �����ݿ�order
	 * @param req
	 * @param resp
	 */
	private void add(HttpServletRequest req, HttpServletResponse resp) {
		//��ȡ���ﳵ�����Ʒ
		Cart cart= (Cart) req.getSession().getAttribute("cart");
		System.out.println("���ϵĳ�����"+cart.getMap().size());
		//��ȡ�µ�������
		//��ȡ�µ���Ʒ��Id
		Forge_Users user=(Forge_Users) req.getSession().getAttribute("forgeUser");
		String num2="";//��¼������ŵ�ת��
		//��ȡ�ܼ�Ǯ
		String cost2 = req.getParameter("cost");
		Double cost = Double.valueOf(cost2);
		//���������(�������)
		 int random=(int)(Math.random()*123456789);
		 String num = String.valueOf(random);
		 System.out.println("==============>"+num);
		 //�������ﳵ���map
		 Iterator a=cart.getMap().entrySet().iterator();
		 while(a.hasNext()){
			Map.Entry entry=(Entry) a.next();
			String key=entry.getKey().toString();
			CartItem item=(CartItem) entry.getValue();
			System.out.println("�������ȡ��Id"+item.getProduct().getId());
			//��ȡ���ݿ�Ķ������
			Forge_Order order = service.findAll(user.getUserId());
			System.out.println("user.getUserId()"+user.getUserId());
			System.out.println(order);
			//������ɵĶ����������ݿ�Ķ��ú��ظ�������������
			 if(num.equals(order.getSerialNumber())){
				 int random2=(int)(Math.random()*123456789);
				 num2 = Integer.toString(random); 
				 System.out.println("���������"+num2);
				 //��ӵ�order
				service.add(user.getUserId(),user.getLoginName(),user.getAddress(),item.getPrice(),num2);
				//�ɹ��µ�������ݿ���Ĺ��ﳵ��Ӧ����Ʒɾ��
				cartService.delete(Integer.toString(user.getUserId()),item.getProduct().getId());
				//��ӵ�detail
				addDetail(order.getId(),Integer.valueOf(item.getProduct().getId()),item.getNum(),item.getPrice());
			 }else{
				 service.add(user.getUserId(),user.getLoginName(),user.getAddress(),item.getPrice(),num);
				 cartService.delete(Integer.toString(user.getUserId()),item.getProduct().getId());
				 addDetail(order.getId(),Integer.valueOf(item.getProduct().getId()),item.getNum(),item.getPrice());
			 }
			
		 }
		 
		
		
		
		
	}
	/**
	 * ����orderId����ƷId����ӵ���̨   �������ܼ�Ǯ
	 * @param req
	 * @param resp
	 */
	private void addDetail(int orderId, int productId, int num, double price) {
		System.out.println("���뵽addDetail");
		Forge_Order_Detail_Dao detail =new Forge_Order_Detail_Dao_Impl();
		detail.add(orderId,productId,num,price);
		
	}

	/*
	 * �����û���ID��ѯ�Լ��Ķ���(ֻ�е�¼���ڿ��Բ�ѯ)
	 */
	private void findById(HttpServletRequest req, HttpServletResponse resp) {
		Forge_Users user=(Forge_Users) req.getSession().getAttribute("forgeUser");
		Forge_Order order = service.findAll(user.getUserId());
	}

}
