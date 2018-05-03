package com.forge.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.forge.bean.Forge_Users;
import com.forge.dao.Forge_Users_Dao;
import com.forge.dao_impl.Forge_Users_Dao_Impl;
import com.forge.service.Forge_Users_Service;
import com.forge.service_impl.Forge_Users_Service_Impl;
import com.sun.mail.iap.Response;

@WebServlet("/forgeServlet")
public class Servlet extends HttpServlet {
	Forge_Users_Dao userDao = new Forge_Users_Dao_Impl();
	Forge_Users_Service service = new Forge_Users_Service_Impl();


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
				if (remember == null) {
					resp.sendRedirect("index.jsp");
				} else {
					// 登录名保存到cookie会话
					resp.addCookie(new Cookie("loginName", "loginName"));
					resp.sendRedirect("index.jsp");
				}

			}
		}
	}
}
