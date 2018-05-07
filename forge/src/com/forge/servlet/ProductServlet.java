package com.forge.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.spy.memcached.MemcachedClient;

import com.forge.bean.Cart;
import com.forge.bean.CartItem;
import com.forge.bean.Forge_Cart;
import com.forge.bean.Forge_Product;
import com.forge.bean.Forge_Users;
import com.forge.dao_impl.Forge_CartServiceImpl;
import com.forge.service.Forge_CartService;
import com.forge.service.Forge_Product_Service;
import com.forge.service_impl.Forge_Product_Service_Impl;
import com.forge.util.MemcachedUtil;
import com.google.gson.Gson;
@WebServlet("/buyServlet")
public class ProductServlet extends HttpServlet{
//ʵ����service�����
	Forge_Product_Service service=new Forge_Product_Service_Impl();
	
	Forge_CartService fcService = new Forge_CartServiceImpl();

	
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
		System.out.println("jspҳ���õ�method===��"+method);
		if(method==null){
			List<Forge_Product>product=service.findAll();
			req.setAttribute("product", product);
			//ת��
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
			case "ifUserLogin":
				ifUserLogin(req,resp);
				break;
			}
		}
		
		
	}
	

	
	
	
	
/**
 * �ж��û��Ƿ��¼ȥ����ķ���
 * @param req
 * @param resp
 */
	private void ifUserLogin(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("����������ifUserLogin");
		//���ж��û��Ƿ��¼�����û�е�¼���빺�ﳵ�ȵ�¼����¼������ѯҳ��
		String user=(String) req.getSession().getAttribute("loginName");
		if(null==user){//�û�û��¼
			System.out.println("�û�û�е�¼ȥ����");
			try {
				resp.sendRedirect("login.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{//�û��ѵ�¼
			System.out.println("�û��ѵ�¼ȥ����");
		Forge_Users forgeUser=	(Forge_Users) req.getSession().getAttribute("forgeUser");
		System.out.println("sssssssssssssssssssssssssssssssssss===>"+forgeUser.getLoginName());
		System.out.println("sssssssssssssssssssssssssssssssssss===>"+forgeUser.getAddress());
		System.out.println("sssssssssssssssssssssssssssssssssss===>"+forgeUser.getPhone());
		System.out.println("sssssssssssssssssssssssssssssssssss===>"+forgeUser.getLoginName());
			try {
				String loginName= (String) req.getSession().getAttribute("loginName");
				System.out.println(loginName);
				resp.sendRedirect("my-add.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void clearCart(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("����������clearCart");
		req.getSession().removeAttribute("cart");
		// ת�������ﳵҳ��
		try {
			resp.sendRedirect("my-car.jsp");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	private void delCart(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("����������delCart");
		String id = req.getParameter("id");
		Cart cart = (Cart) req.getSession().getAttribute("cart");
		service.delCart(id, cart);
		try {
			resp.sendRedirect("my-car.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
/**
 * ��ѯ���ﳵ����
 * @param req
 * @param resp
 */
	private void findCart(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("��ӭ����findCart����");
		//���ж��û��Ƿ��¼�����û�е�¼���빺�ﳵ�ȵ�¼����¼������ѯҳ��
		Forge_Users user=(Forge_Users) req.getSession().getAttribute("user");
		if(null==user){//�û����ڿ�
			//�û�û�е�¼��ѯcookie�ﹺ�ﳵ
			Cookie[] cookies = req.getCookies();
			Cookie cookie=null;
			for (int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("cart")){
					//�ҵ��ĸ���cookie
					cookie=cookies[i];
				}
			}
			if(null==cookie){//cookie���ڿ�
				System.out.println("������cookie���ڿ�");
				try {
					resp.sendRedirect("my-car.jsp");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{//cookie�����ڿ�
				System.out.println("������findCart����cookie�����ڿ�");
				String json=cookie.getValue();
				Gson gson=new Gson();
				Cart cart=gson.fromJson(json, Cart.class);
				req.getSession().setAttribute("cart", cart);
				try {
					resp.sendRedirect("my-car.jsp");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				req.setAttribute("cart", cart);
//				try {
//					req.getRequestDispatcher("my-car.jsp").forward(req, resp);
//				} catch (ServletException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		}else{
			//�û���Ϊ�գ��û��Ѿ���¼  �ȴӻ�����ȡ���ﳵ�����������û�У�ȥ���ݿ�ȡ
			System.out.println("++++++++++++findCart:user��Ϊ��=================");
			MemcachedClient client = MemcachedUtil.getInstance();
			Cart cart = (Cart) client.get("cart");
			
			if(cart!=null){//����������й��ﳵ
				System.out.println("++++++++++++findCart:Memcachedcart��Ϊ��=================");
				req.getSession().setAttribute("cart", cart);
			}else{//���������û�й��ﳵ  ȥ���ݿ�ȡ
				//�����ݿ���ȡ�����ﳵ
				System.out.println("++++++++++++findCart:MemcachedcartΪ��=================");
				 Cart userCart = getUserCart(user.getUserId());
				req.getSession().setAttribute("cart", cart);

			}
			try {
				resp.sendRedirect("my-car.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}

			
			
		}
		
	}
/**
 * ����Ʒ���빺�ﳵ
 * @param req
 * @param resp
 */
	private void addCart(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("method������addCart");
		//��ȡ��Ʒ��id
		String productId = req.getParameter("id");
		//��ȡ��Ʒ������
		String num = req.getParameter("number");
		int num2 =Integer.valueOf(num);
		System.out.println("��Ʒ������"+num2);
		System.out.println("productId====>"+productId);
		//��ȡsession���user
		Forge_Users user= (Forge_Users) req.getSession().getAttribute("user");
		//��ȡcookie����
		Cookie[]cookies=req.getCookies();
		//����cookie����cart��cookies[i]��
		
		//����cart����
		Cart cart = null;
		//����json
		String json;
		//����gson
		Gson gson = new Gson();
		if(null==user){//֤���û����ڿ�
			Cookie cookie =null;
			System.out.println("if��user========��"+user);
			//����cookie����Ѱ��cart
			for (int i=0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("cart")){
					//�ҵ��ĸ���cookie
					cookie=cookies[i];
				}
				
			}
			if(null==cookie){//cookie��û�й��ﳵ
				System.out.println("������addCart����cookie���ڿ�");
				cart = new Cart();
				//��ӵ����ﳵ     num2����
				service.addCart(productId, cart, num2);
				System.out.println("=================Ǯ"+cart.getPrice());
				//��String���͵�cartת����json
				json = gson.toJson(cart);
				//cookie��û�й��ﳵ�������ﳵcookie
				cookie = new Cookie("cart",json);
				//����cookie����Ч��
				cookie.setMaxAge(24*60*60);
				//���cookie
				resp.addCookie(cookie);
			}else{//cookie���й��ﳵ
				System.out.println("������addCart����cookie�����ڿ�");
				json = cookie.getValue();
				cart = gson.fromJson(json, Cart.class);
				System.out.println("=================����ǰǮ"+cart.getPrice());
				//����id��ӹ��ﳵ
				System.out.println("������cookie�����ڿ�"+productId);
				service.addCart(productId, cart, num2);
			    System.out.println(">>>>>>>>>>>>>>map�ĳ���"+cart.getMap().size());
				json = gson.toJson(cart);
				//��json���cookie��cart��
				cookie.setValue(json);
				String sj = cookie.getValue();
				Cart ca = gson.fromJson(json, Cart.class);
				System.out.println(ca.getMap().size());
				System.out.println("=================�����Ǯ"+cart.getPrice());
			}
			
		}else{//֤���û������ڿ�,˵��session����user,��ʱ�ͳ־û����ﳵ��Ϣ
			System.out.println("else��user========��"+user);
			System.out.println("++++++++++++findCart:user��Ϊ��=================");
			MemcachedClient client = MemcachedUtil.getInstance();
			Cart cart2 = (Cart) client.get("cart");
			
			if(cart2!=null){//����������й��ﳵ
				System.out.println("++++++++++++findCart:Memcachedcart��Ϊ��=================");
				req.getSession().setAttribute("cart", cart2);
			}else{//���������û�й��ﳵ  ȥ���ݿ�ȡ
				//�����ݿ���ȡ�����ﳵ
				System.out.println("++++++++++++findCart:MemcachedcartΪ��=================");
				 Cart userCart = getUserCart(user.getUserId());
				req.getSession().setAttribute("cart", cart2);

			}
			try {
				resp.sendRedirect("my-car.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		try {
			resp.sendRedirect("page.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//��ȡ�û��Ĺ��ﳵ
			private Cart getUserCart(String userId) {
				System.out.println("==========������getUserCart==============");

				Cart cart = new Cart();
				List<Forge_Cart> item = fcService.findByUserId(userId);
				for(int i = 0;i<item.size();i++){
					//��ȡ��Ʒ��id
					String productId = item.get(i).getProductId();
					//������Ʒid��ȡ��Ʒ
					Forge_Product product = service.findById(productId);
					//��ȡ��Ʒ����
					String num =item.get(i).getProductNum();
					//��ȡ��ƷС��
					double price = item.get(i).getPrice();
					//�������������Ʒ���빺������
					 CartItem cartItem = new CartItem();
					 cartItem.setNum(Integer.valueOf(num));
					 cartItem.setPrice(price);
					 cartItem.setProduct(product);
					
					cart.getMap().put(productId, cartItem);
				}
				return cart;
			}

}
