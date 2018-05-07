package com.forge.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.forge.bean.Cart;
import com.forge.bean.CartItem;
import com.forge.bean.Forge_Cart;
import com.forge.bean.Forge_Product;
import com.forge.bean.Forge_Users;
import com.forge.dao.Forge_Users_Dao;
import com.forge.dao_impl.Forge_CartServiceImpl;
import com.forge.dao_impl.Forge_Users_Dao_Impl;
import com.forge.service.Forge_CartService;
import com.forge.service.Forge_Product_Service;
import com.forge.service.Forge_Users_Service;
import com.forge.service_impl.Forge_Product_Service_Impl;
import com.forge.service_impl.Forge_Users_Service_Impl;
import com.forge.util.MemcachedUtil;
import com.google.gson.Gson;
import com.sun.mail.iap.Response;

@WebServlet("/forgeServlet")
public class Servlet extends HttpServlet {
	Forge_Users_Dao userDao = new Forge_Users_Dao_Impl();
	Forge_Users_Service service = new Forge_Users_Service_Impl();

	Forge_Product_Service pservice = new Forge_Product_Service_Impl();
	Forge_CartService fcService = new Forge_CartServiceImpl();

	
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String method = req.getParameter("method");
		System.out.println("����������" + method);
		switch (method) {
		case "login":
			login(req, resp);
			break;
		case "validate":
			validateLoginName(req, resp);
			break;
		case "add":
			try {
				add(req, resp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

	}

	/**
	 * ע�� ��������
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void add(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		
		System.out.println("������add����");
		String id = req.getParameter("id");
		req.setCharacterEncoding("UTF-8");
		String password = req.getParameter("password");
		String repassword = req.getParameter("repassword");

		Forge_Users user = new Forge_Users();

		user.setUserId(id);
		user.setAddress(req.getParameter("address"));
		user.setEmail(req.getParameter("email"));
		user.setPhone(req.getParameter("phone"));
		user.setLoginName(req.getParameter("loginName"));
		user.setPassword(password);
		service.add(user);
		System.out.println("ִ�����");
		resp.sendRedirect("login.jsp");

	}
	
	public void validateLoginName(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		//����userʵ������
		Forge_Users user=null;
		//�ж����ݿ����Ƿ�����û�������û�
		String loginName = req.getParameter("loginName");
		System.out.println("=====================++++"+loginName);
		user = service.findByName(loginName);

				boolean flag=false;
				System.out.println(loginName);
				if(user!=null){
					flag=true;//֤�����ݿ����
					System.out.println("______________________________________");
				}
				PrintWriter writer = resp.getWriter();
				writer.print(flag);
				writer.close();

	}

	// //����factory���� �������û�������С �Լ����λ��
	// DiskFileItemFactory factory = new DiskFileItemFactory();
	// ServletFileUpload upload=new ServletFileUpload(factory);
	// Forge_Users user=new Forge_Users();
	// boolean flag = upload.isMultipartContent(req);
	// System.out.println(flag);
	// if(flag){//form�����ļ��ϴ�����
	//
	//
	// List<FileItem> items=upload.parseRequest(req);
	// //ʹ�õ��������� Ч�ʸ�
	// Iterator<FileItem> its = items.iterator();
	// while(its.hasNext()){
	// FileItem item = its.next();
	// //�жϱ�Ԫ����ʲô����
	// if(item.isFormField()){//֤������ͨ��Ԫ��
	// String fieldName = item.getFieldName();// title context
	// //createTime
	// switch(fieldName){
	// case "loginName":
	// user.setLoginName(item.getString("UTF-8"));
	// break;
	// case "phone":
	// user.setPhone(item.getString("UTF-8"));
	// break;
	// case "address":
	// user.setAddress(item.getString("UTF-8"));
	// case "email":
	// user.setEmail(item.getString("UTF-8"));
	// case "password":
	// if()
	// break;
	// }
	//
	// }
	// }}
	//

	// }

	/**
	 * ��¼
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void login(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html;charset=UTF-8");
		// 解决请求编码
		req.setCharacterEncoding("utf-8");
		// ��ȡout���
		PrintWriter out = resp.getWriter();
		// ��ȡ �û�����
		String loginName = req.getParameter("loginName");
		String password = req.getParameter("password");
		// ��ȡ�����ֵ ���չ�����0��
		String reslut = req.getParameter("slider_block");
		// ���ռ�ס����
		String remember = req.getParameter("remember");
		// �����û�����
		Forge_Users user = new Forge_Users();
		// ���õ�¼����
		user = userDao.login(loginName, password);
		// �߼��ж�
		if (user == null) {
			JOptionPane.showMessageDialog(null, "�û������벻��ȷ");
			resp.sendRedirect("login.jsp");
		} else {
			
			if (reslut.equals("0")) {
				JOptionPane.showMessageDialog(null, "�뻬��������¼");
				resp.sendRedirect("login.jsp");
			} else {
				//��¼�ɹ�����ס�û���
				if (remember == null) {
					req.getSession().setAttribute("loginName", loginName);
					req.getSession().setAttribute("forgeUser", user);
				} else {//��¼�ɹ���ס�û���
					// ��¼�����浽session
					req.getSession().setAttribute("loginName", loginName);
					req.getSession().setAttribute("forgeUser", user);
				}
				//�ϲ����ﳵ
				Cart cart = mergeCart1(user,req,resp);
				System.out.println("+++++++++yҪ���뻺���е�cart:"+cart);
				//�����ﳵ���ڻ�����     getInstance��ȡMemcachedClientʵ��
				MemcachedClient client = MemcachedUtil.getInstance();
				//�洢key  value
				client.set("cart",1000,cart);
				
				try {
						resp.sendRedirect("index.jsp");
						} catch (IOException e) {
							e.printStackTrace();
						}
			}
			
			
			
		}
	}
	
	
	private Cart mergeCart1(Forge_Users user,HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("==========������mergeCart1==============");
			//�����ݿ���ȡ�����ﳵ
		  Cart userCart = getUserCart(user.getUserId());
		  //��cookie��ȡ�����ﳵ
		  Cart cookieCart = getCookieCart(req,resp);
		  //�������ﳵ���кϲ�
		  Cart mergeCart = mergeCart(userCart,cookieCart);
		
		return mergeCart;
	}
	//��ȡ�û��Ĺ��ﳵ
		private Cart getUserCart(String userId) {
			System.out.println("==========������getUserCart==============");
			//����һ�����ﳵ
			Cart cart = new Cart();
			//��Ʒ�ܼ�
			double price = 0;  
			List<Forge_Cart> item = fcService.findByUserId(userId);
			for(int i = 0;i<item.size();i++){
				//��ȡ��Ʒ��id
				String productId = item.get(i).getProductId();
				//������Ʒid��ȡ��Ʒ
				Forge_Product product = pservice.findById(productId);
				//��ȡ��Ʒ����
				String num =item.get(i).getProductNum();
				//��ȡ��ƷС��
				double price1 = item.get(i).getPrice();
				//�������������Ʒ���빺������
				 CartItem cartItem = new CartItem();
				 cartItem.setNum(Integer.valueOf(num));
				 cartItem.setPrice(price1);
				 cartItem.setProduct(product);
				cart.getMap().put(productId, cartItem);
				price+=item.get(i).getPrice();  //ѭ����ȡ�ܼ�
			}
			cart.setCount(cart.getCount()+item.size());
			cart.setPrice(cart.getPrice()+price);
			System.out.println("==========������getUserCart cart:=============="+cart);
			return cart;
		}

	
		//��cookie��ȡ�����ﳵ
		private Cart getCookieCart(HttpServletRequest req, HttpServletResponse resp) {
			System.out.println("==========������getCookieCart==============");
			String json = null;
			Cookie cookie = null;
			//��cookie�в��ҹ��ﳵ
			Cookie  [] cookies = req.getCookies();
			for(int i = 0;i<cookies.length;i++){
				if(cookies[i].getName().equals("cart")){
					cookie = cookies[i];
					json = cookie.getValue();
				}
			}
			Gson gson = new Gson();
			Cart cart = gson.fromJson(json, Cart.class);
			System.out.println("==========������getCookieCart==============:"+cart);

			return cart;
		}

		private Cart mergeCart(Cart userCart, Cart cookieCart) {
			System.out.println("==========������mergeCart==============");

			Cart cart = null;
			//�ж��������ﳵ�Ƿ�Ϊ��
			if(userCart!=null&&cookieCart==null){
				return cookieCart;
			}
			if(userCart==null&&cookieCart!=null){
				return cookieCart;
			}
			if(userCart!=null&&cookieCart!=null){
				//���߶���Ϊ�հ�cookieCart���뵽userCart
				userCart.setCount(userCart.getCount()+cookieCart.getCount());
				userCart.setPrice(userCart.getPrice()+cookieCart.getPrice());
				//��ȡcookieCart��map
				Map<String,CartItem> map = cookieCart.getMap();
				//����map������userCart
				Iterator it = map.entrySet().iterator();
				while(it.hasNext()){
					Map.Entry entry = (Map.Entry) it.next();
					String key = entry.getKey().toString();
					CartItem item = (CartItem) entry.getValue();
					userCart.getMap().put(key, item);
					userCart.setCount(userCart.getCount());
				}
				return userCart;
			}
			
			return cart;
		}

	
}
