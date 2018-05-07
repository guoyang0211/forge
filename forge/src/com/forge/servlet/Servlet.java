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
		System.out.println("方法进来的" + method);
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
	 * 注册 （新增）
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void add(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		
		System.out.println("进入了add方法");
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
		System.out.println("执行完毕");
		resp.sendRedirect("login.jsp");

	}
	
	public void validateLoginName(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		//创建user实例对象
		Forge_Users user=null;
		//判断数据库里是否存在用户输入的用户
		String loginName = req.getParameter("loginName");
		System.out.println("=====================++++"+loginName);
		user = service.findByName(loginName);

				boolean flag=false;
				System.out.println(loginName);
				if(user!=null){
					flag=true;//证明数据库存在
					System.out.println("______________________________________");
				}
				PrintWriter writer = resp.getWriter();
				writer.print(flag);
				writer.close();

	}

	// //创建factory对象 可以设置缓冲区大小 以及存放位置
	// DiskFileItemFactory factory = new DiskFileItemFactory();
	// ServletFileUpload upload=new ServletFileUpload(factory);
	// Forge_Users user=new Forge_Users();
	// boolean flag = upload.isMultipartContent(req);
	// System.out.println(flag);
	// if(flag){//form表单是文件上传类型
	//
	//
	// List<FileItem> items=upload.parseRequest(req);
	// //使用迭代器遍历 效率高
	// Iterator<FileItem> its = items.iterator();
	// while(its.hasNext()){
	// FileItem item = its.next();
	// //判断表单元素是什么类型
	// if(item.isFormField()){//证明是普通表单元素
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
	 * 登录
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void login(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html;charset=UTF-8");
		// 瑙ｅ喅璇锋眰缂栫爜
		req.setCharacterEncoding("utf-8");
		// 获取out输出
		PrintWriter out = resp.getWriter();
		// 获取 用户输入
		String loginName = req.getParameter("loginName");
		String password = req.getParameter("password");
		// 获取滑块的值 接收过来是0；
		String reslut = req.getParameter("slider_block");
		// 接收记住密码
		String remember = req.getParameter("remember");
		// 创建用户对象
		Forge_Users user = new Forge_Users();
		// 调用登录方法
		user = userDao.login(loginName, password);
		// 逻辑判断
		if (user == null) {
			JOptionPane.showMessageDialog(null, "用户或密码不正确");
			resp.sendRedirect("login.jsp");
		} else {
			
			if (reslut.equals("0")) {
				JOptionPane.showMessageDialog(null, "请滑动滑块后登录");
				resp.sendRedirect("login.jsp");
			} else {
				//登录成功不记住用户名
				if (remember == null) {
					req.getSession().setAttribute("loginName", loginName);
					req.getSession().setAttribute("forgeUser", user);
				} else {//登录成功记住用户名
					// 登录名保存到session
					req.getSession().setAttribute("loginName", loginName);
					req.getSession().setAttribute("forgeUser", user);
				}
				//合并购物车
				Cart cart = mergeCart1(user,req,resp);
				System.out.println("+++++++++y要存入缓存中的cart:"+cart);
				//将购物车存在缓存中     getInstance获取MemcachedClient实例
				MemcachedClient client = MemcachedUtil.getInstance();
				//存储key  value
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
		System.out.println("==========进入了mergeCart1==============");
			//从数据库中取出购物车
		  Cart userCart = getUserCart(user.getUserId());
		  //从cookie中取出购物车
		  Cart cookieCart = getCookieCart(req,resp);
		  //两个购物车进行合并
		  Cart mergeCart = mergeCart(userCart,cookieCart);
		
		return mergeCart;
	}
	//获取用户的购物车
		private Cart getUserCart(String userId) {
			System.out.println("==========进入了getUserCart==============");
			//创建一个购物车
			Cart cart = new Cart();
			//商品总价
			double price = 0;  
			List<Forge_Cart> item = fcService.findByUserId(userId);
			for(int i = 0;i<item.size();i++){
				//获取商品的id
				String productId = item.get(i).getProductId();
				//根据商品id获取商品
				Forge_Product product = pservice.findById(productId);
				//获取商品数量
				String num =item.get(i).getProductNum();
				//获取商品小计
				double price1 = item.get(i).getPrice();
				//创建购物项将上商品加入购物项中
				 CartItem cartItem = new CartItem();
				 cartItem.setNum(Integer.valueOf(num));
				 cartItem.setPrice(price1);
				 cartItem.setProduct(product);
				cart.getMap().put(productId, cartItem);
				price+=item.get(i).getPrice();  //循环获取总价
			}
			cart.setCount(cart.getCount()+item.size());
			cart.setPrice(cart.getPrice()+price);
			System.out.println("==========进入了getUserCart cart:=============="+cart);
			return cart;
		}

	
		//从cookie中取出购物车
		private Cart getCookieCart(HttpServletRequest req, HttpServletResponse resp) {
			System.out.println("==========进入了getCookieCart==============");
			String json = null;
			Cookie cookie = null;
			//从cookie中查找购物车
			Cookie  [] cookies = req.getCookies();
			for(int i = 0;i<cookies.length;i++){
				if(cookies[i].getName().equals("cart")){
					cookie = cookies[i];
					json = cookie.getValue();
				}
			}
			Gson gson = new Gson();
			Cart cart = gson.fromJson(json, Cart.class);
			System.out.println("==========进入了getCookieCart==============:"+cart);

			return cart;
		}

		private Cart mergeCart(Cart userCart, Cart cookieCart) {
			System.out.println("==========进入了mergeCart==============");

			Cart cart = null;
			//判断两个购物车是否为空
			if(userCart!=null&&cookieCart==null){
				return cookieCart;
			}
			if(userCart==null&&cookieCart!=null){
				return cookieCart;
			}
			if(userCart!=null&&cookieCart!=null){
				//两者都不为空把cookieCart加入到userCart
				userCart.setCount(userCart.getCount()+cookieCart.getCount());
				userCart.setPrice(userCart.getPrice()+cookieCart.getPrice());
				//获取cookieCart的map
				Map<String,CartItem> map = cookieCart.getMap();
				//遍历map并加入userCart
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
