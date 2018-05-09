package com.forge.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import com.forge.bean.region;
import com.forge.dao.Forge_Users_Dao;
import com.forge.dao_impl.Forge_Users_Dao_Impl;
import com.forge.service.Forge_CartService;
import com.forge.service.Forge_Product_Service;
import com.forge.service.Forge_Users_Service;
import com.forge.service_impl.Forge_CartServiceImpl;
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
		case "exit":
			userExit(req, resp);
			break;
		case "add":
			try {
				add(req, resp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case "search":
			search(req,resp);
			break;
		case "address":
			myAdd(req,resp);
			break;
		}

	}
	
	/**
	 * ���������ת����Ӧ��ҳ��
	 * @param req
	 * @param resp
	 */
	private void search(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.setCharacterEncoding("utf-8");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.setCharacterEncoding("utf-8");
		System.out.println("��������������");
		//��ȡ�û�Ҫ�������ֶ�
		String userInput = req.getParameter("input");
		System.out.println("�û�Ҫ��������"+userInput);
	}

	/**
	 * ������������
	 */
		private void myAdd(HttpServletRequest req, HttpServletResponse resp) {
			String parentId = req.getParameter("parentId");
			if (parentId==null || parentId=="") {
				parentId="0";//������ĸ����Ϊ�գ���Ĭ�ϸ�ֵΪ�й��ĸ���㣬Ҳ����ÿһ����ѯ����ʡ��
			}
			List<region> list = service.findAddress(parentId);
			String json="";
			Gson gson=new Gson();
			json=gson.toJson(list);
			resp.setCharacterEncoding("utf-8");
			try {
				resp.getWriter().print(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	
/**
 * �û������˳�����
 * @param req
 * @param resp
 */
	private void userExit(HttpServletRequest req, HttpServletResponse resp) {
	System.out.println("�����˵����û������˳�");
	//��ȡ�û���id
	String id = req.getParameter("id");
	System.out.println("�û���id=====��"+id);
	//�����ݿ��л�ȡ��λ�û���������Ʒ
	List<Forge_Cart> findAll = fcService.findAll(id);
	//��ȡ�����й��ﳵ
	MemcachedClient client= MemcachedUtil.getInstance();
	Cart cart= (Cart) client.get("cart");
	//����������
	Iterator mit=cart.getMap().entrySet().iterator();
	while(mit.hasNext()){
		//��ȡ���˼����е�key,value
		Map.Entry entry= (Entry) mit.next();
		//key������Ʒ��id
		String key= (String) entry.getKey().toString();
		//��Ӧ�Ĺ�����
		CartItem item=(CartItem) entry.getValue();
		if(findAll.size()==0){
			fcService.add(id,item.getProduct().getId(),item.getNum(),item.getPrice());
		}else{
			for(int i = 0; i < findAll.size(); i++) {
				if(item.getProduct().getId().equals(findAll.get(i).getProductId())){
					System.out.println("������if");
//					String num = item.getNum()+findAll.get(i).getProductNum();
//					double price = item.getPrice()+findAll.get(i).getPrice();
					fcService.update((item.getNum()),(item.getPrice()),id,item.getProduct().getId());
					System.out.println("������"+(item.getNum()+findAll.get(i).getProductNum()));
					System.out.println("�ܼ�Ǯ"+(item.getPrice()+findAll.get(i).getPrice()));
				}else{
					System.out.println("������else");
					System.out.println("findAll.get(i).getProductId()"+findAll.get(i).getProductId());
					System.out.println("item.getProduct().getId()"+item.getProduct().getId());
					fcService.add(id,item.getProduct().getId(),item.getNum(),item.getPrice());
					
					
					
				}
				
				
			}
		}
		
		
	}
	/**
	 * �����������
	 * 
	 */
	clearAll(req, resp, client);
	  
		try {//�����˳��������ҳ��
			resp.sendRedirect("index.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * �����������
	 * @param req
	 * @param resp
	 * @param client
	 */
public void clearAll(HttpServletRequest req, HttpServletResponse resp,
		MemcachedClient client) {
	//���cookie
	Cookie[] cookies = req.getCookies();
	for (Cookie c : cookies) {
		c=new Cookie(c.getName(),null);
		c.setPath("/");
		c.setMaxAge(-0);
		resp.addCookie(c);
	}
	//ʹsessionʧЧ
	req.getSession().invalidate();
	//��ջ���
	    if(client.delete("cart").equals(client.get("cart"))){
	    	System.out.println("ɾ��ʧ��");
	    }else{
	    	System.out.println("ɾ���ɹ�");
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
				client.add("cart",1000,cart);
				try {
						resp.sendRedirect("index.jsp");
						} catch (IOException e) {
							e.printStackTrace();
						}
			}
			
			
			
		}
	}
	
	/**
	 * �ϲ����ﳵ����
	 * @param user
	 * @param req
	 * @param resp
	 * @return
	 */
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
				int num =item.get(i).getProductNum();
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
			cart.setPrice(cart.getPrice());
			System.out.println("==========������getUserCart cart:=============="+cart);
			return cart;
		}

	
		//��cookie��ȡ�����ﳵ
		private Cart getCookieCart(HttpServletRequest req, HttpServletResponse resp) {
			System.out.println("==========������getCookieCart==============");
			String json = null;
			Cookie cookie = null;
			Cart cart = null;
			//��cookie�в��ҹ��ﳵ
			Cookie  [] cookies = req.getCookies();
			for(int i = 0;i<cookies.length;i++){
				if(cookies[i].getName().equals("cart")){
					cookie = cookies[i];
					json = cookie.getValue();
				}
			}
			if(json!=null){
				Gson gson = new Gson();
			   cart = gson.fromJson(json, Cart.class);
			}
		
			System.out.println("==========������getCookieCart==============:"+cart);

			return cart;
		}
		//�ϲ����ﳵ
		private Cart mergeCart(Cart userCart, Cart cookieCart) {
			System.out.println("==========������mergeCart==============");

			Cart cart = null;
			//�ж��������ﳵ�Ƿ�Ϊ��
			if(userCart!=null&&cookieCart==null){
				return userCart;
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
				//��ȡuserCart��map
				Map<String,CartItem> userMap = userCart.getMap();
				//����map������userCart
				Iterator it = map.entrySet().iterator();
				Iterator uit = userMap.entrySet().iterator();
				while(it.hasNext()){
					Map.Entry entry = (Map.Entry) it.next();
					//ȡ��cookieCart��map��key,value
					String key = entry.getKey().toString();
					CartItem item = (CartItem) entry.getValue();
					//����userMap
					while(uit.hasNext()){
						Map.Entry uentry = (Map.Entry) uit.next();
						String ukey = uentry.getKey().toString();
						if(key.equals(ukey)){//����û���ͬ����Ʒ��ͬ����Ʒ����������Ǯ���
							//ȡ���û��Ĺ�����
							CartItem uitem = (CartItem) uentry.getValue();
							//���úϲ��������
							uitem.setNum(uitem.getNum());
							System.out.println("�ϲ��������"+uitem.getNum());
							//���úϲ���ļ�Ǯ
							uitem.setPrice(uitem.getPrice());
							System.out.println("�ϲ���ļ�Ǯ"+uitem.getPrice());
							System.out.println("�����˺ϲ�if");
						}else{
							System.out.println("�����˺ϲ�else");
							userCart.getMap().put(key, item);
						}
					}
				userCart.setCount(userCart.getCount());
				}
				return userCart;
			}
			
			return cart;
		}

	
}
