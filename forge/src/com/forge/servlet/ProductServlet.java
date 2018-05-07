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
//实例化service层对象
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
			case "ifUserLogin":
				ifUserLogin(req,resp);
				break;
			}
		}
		
		
	}
	

	
	
	
	
/**
 * 判断用户是否登录去付款的方法
 * @param req
 * @param resp
 */
	private void ifUserLogin(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("方法进来的ifUserLogin");
		//先判断用户是否登录，如果没有登录进入购物车先登录，登录则进入查询页面
		String user=(String) req.getSession().getAttribute("loginName");
		if(null==user){//用户没登录
			System.out.println("用户没有登录去结账");
			try {
				resp.sendRedirect("login.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{//用户已登录
			System.out.println("用户已登录去结账");
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
		System.out.println("方法进来的clearCart");
		req.getSession().removeAttribute("cart");
		// 转发到购物车页面
		try {
			resp.sendRedirect("my-car.jsp");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	private void delCart(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("方法进来的delCart");
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
 * 查询购物车方法
 * @param req
 * @param resp
 */
	private void findCart(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("欢迎来到findCart方法");
		//先判断用户是否登录，如果没有登录进入购物车先登录，登录则进入查询页面
		Forge_Users user=(Forge_Users) req.getSession().getAttribute("user");
		if(null==user){//用户等于空
			//用户没有登录查询cookie里购物车
			Cookie[] cookies = req.getCookies();
			Cookie cookie=null;
			for (int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("cart")){
					//找到的赋给cookie
					cookie=cookies[i];
				}
			}
			if(null==cookie){//cookie等于空
				System.out.println("进入了cookie等于空");
				try {
					resp.sendRedirect("my-car.jsp");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{//cookie不等于空
				System.out.println("进入了findCart方法cookie不等于空");
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
			//用户不为空，用户已经登录  先从缓存中取购物车，如果缓存中没有，去数据库取
			System.out.println("++++++++++++findCart:user不为空=================");
			MemcachedClient client = MemcachedUtil.getInstance();
			Cart cart = (Cart) client.get("cart");
			
			if(cart!=null){//如果缓存中有购物车
				System.out.println("++++++++++++findCart:Memcachedcart不为空=================");
				req.getSession().setAttribute("cart", cart);
			}else{//如果缓存中没有购物车  去数据库取
				//从数据库中取出购物车
				System.out.println("++++++++++++findCart:Memcachedcart为空=================");
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
 * 把商品加入购物车
 * @param req
 * @param resp
 */
	private void addCart(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("method进来的addCart");
		//获取商品的id
		String productId = req.getParameter("id");
		//获取商品的数量
		String num = req.getParameter("number");
		int num2 =Integer.valueOf(num);
		System.out.println("商品的数量"+num2);
		System.out.println("productId====>"+productId);
		//获取session里的user
		Forge_Users user= (Forge_Users) req.getSession().getAttribute("user");
		//获取cookie集合
		Cookie[]cookies=req.getCookies();
		//创建cookie接收cart（cookies[i]）
		
		//创建cart对象
		Cart cart = null;
		//创建json
		String json;
		//创建gson
		Gson gson = new Gson();
		if(null==user){//证明用户等于空
			Cookie cookie =null;
			System.out.println("if里user========》"+user);
			//遍历cookie集合寻找cart
			for (int i=0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("cart")){
					//找到的赋给cookie
					cookie=cookies[i];
				}
				
			}
			if(null==cookie){//cookie中没有购物车
				System.out.println("进入了addCart方法cookie等于空");
				cart = new Cart();
				//添加到购物车     num2数量
				service.addCart(productId, cart, num2);
				System.out.println("=================钱"+cart.getPrice());
				//把String类型的cart转换成json
				json = gson.toJson(cart);
				//cookie里没有购物车创建购物车cookie
				cookie = new Cookie("cart",json);
				//设置cookie的有效期
				cookie.setMaxAge(24*60*60);
				//添加cookie
				resp.addCookie(cookie);
			}else{//cookie中有购物车
				System.out.println("进入了addCart方法cookie不等于空");
				json = cookie.getValue();
				cart = gson.fromJson(json, Cart.class);
				System.out.println("=================加入前钱"+cart.getPrice());
				//根据id添加购物车
				System.out.println("进入了cookie不等于空"+productId);
				service.addCart(productId, cart, num2);
			    System.out.println(">>>>>>>>>>>>>>map的长度"+cart.getMap().size());
				json = gson.toJson(cart);
				//把json存进cookie（cart）
				cookie.setValue(json);
				String sj = cookie.getValue();
				Cart ca = gson.fromJson(json, Cart.class);
				System.out.println(ca.getMap().size());
				System.out.println("=================加入后钱"+cart.getPrice());
			}
			
		}else{//证明用户不等于空,说明session存在user,这时就持久化购物车信息
			System.out.println("else里user========》"+user);
			System.out.println("++++++++++++findCart:user不为空=================");
			MemcachedClient client = MemcachedUtil.getInstance();
			Cart cart2 = (Cart) client.get("cart");
			
			if(cart2!=null){//如果缓存中有购物车
				System.out.println("++++++++++++findCart:Memcachedcart不为空=================");
				req.getSession().setAttribute("cart", cart2);
			}else{//如果缓存中没有购物车  去数据库取
				//从数据库中取出购物车
				System.out.println("++++++++++++findCart:Memcachedcart为空=================");
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
	
	
	//获取用户的购物车
			private Cart getUserCart(String userId) {
				System.out.println("==========进入了getUserCart==============");

				Cart cart = new Cart();
				List<Forge_Cart> item = fcService.findByUserId(userId);
				for(int i = 0;i<item.size();i++){
					//获取商品的id
					String productId = item.get(i).getProductId();
					//根据商品id获取商品
					Forge_Product product = service.findById(productId);
					//获取商品数量
					String num =item.get(i).getProductNum();
					//获取商品小计
					double price = item.get(i).getPrice();
					//创建购物项将上商品加入购物项中
					 CartItem cartItem = new CartItem();
					 cartItem.setNum(Integer.valueOf(num));
					 cartItem.setPrice(price);
					 cartItem.setProduct(product);
					
					cart.getMap().put(productId, cartItem);
				}
				return cart;
			}

}
