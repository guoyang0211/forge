package com.forge.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import Forge_News_service.Forge_Users_Service;
import Forge_News_service_Impl.Forge_Users_Service_Impl;

import com.forge_CMS.bean.Forge_Users;
import com.forge_CMS.dao.Forge_Users_Dao;
import com.forge_CMS.dao_Impl.Forge_Users_Dao_Impl;
@WebServlet("/UserServlet/*")
public class UserServlet extends HttpServlet {

	Forge_Users_Dao userDao= new Forge_Users_Dao_Impl();

	Forge_Users_Service service=new Forge_Users_Service_Impl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//设置post乱码
		req.setCharacterEncoding("utf-8");
		String method=req.getParameter("method");
		System.out.println(method);
	
		switch(method){
		case "addUser":
			addUser(req,resp);// 新增的方法
			break;
		case "findAll":
			findAllUser(req,resp);// 查询的方法
			break;
		case "findById":
			findById(req,resp);// 查询的方法
			break;
		case "delete":
			delUser(req,resp);// 删除的方法
			break;
		case "update":
			updateUser(req,resp);// 修改方法
			break;
		}
			
	
		
		
	}
//修改用户方法
	private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String id=req.getParameter("id");
		String name=req.getParameter("loginName");
		String userPhone=req.getParameter("phone");
		String email=req.getParameter("email");
		String address=req.getParameter("address");
		Forge_Users user=new Forge_Users( name, userPhone, email, address);
	
		boolean update = service.update(id,user);
		if(update){
			System.out.println("修改成功");
			resp.sendRedirect("/forge_CMS/UserServlet?method=findAll");
		}else{
			JOptionPane.showMessageDialog(null, "修改失败");
			System.out.println("修改失败");
			resp.sendRedirect("/forge_CMS/UserServlet?method=findById&id="+id+"");

		}
		
		//user.setUserId(Integer.valueOf(id));
		
/*		Forge_Users user1 = (Forge_Users)req.getSession().getAttribute("user");
		int id =user1.getUserId();
	
	
		user1.setUserId(Integer.valueOf(id));
		user1.setLoginName(req.getParameter("loginName"));
		System.out.println("loginName"+req.getParameter("loginName"));
		
		user1.setPhone(req.getParameter("userPhone"));
		System.out.println("userPhone"+req.getParameter("userPhone"));
		
		user1.setEmail(req.getParameter("email"));
		System.out.println("email"+req.getParameter("email"));
		
		user1.setAddress(req.getParameter("address"));
		System.out.println("address"+req.getParameter("address"));
		System.out.println(user1.toString());
		System.out.println(user1.getLoginName());
		System.out.println(user1.getEmail());
		System.out.println(user1.getUserId());
		boolean flag=service.update(user1);
		if(flag){
			System.out.println("修改成功");
			resp.sendRedirect("production/tables_dynamic.jsp");
		}else{
			JOptionPane.showMessageDialog(null, "修改失败");
			System.out.println("修改失败");
			//resp.sendRedirect("production/User_Info_table.jsp");
			try {
				req.getRequestDispatcher("production/User_Info_table.jsp").forward(req, resp);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
*/	
	
	}
//删除用户方法
	private void delUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String id = req.getParameter("id");
		System.out.println(id);
	
		int num = service.delete(id);
		if(num!=0){
			System.out.println("成功");
			try {
				resp.sendRedirect("/forge_CMS/UserServlet?method=findAll");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			
			resp.sendRedirect("/forge_CMS/UserServlet?method=findAll");
		}
		
	}
//根据id查询
	private void findById(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		String id = req.getParameter("id");
		Forge_Users user=null;
		user = service.findById(id);
		// 保存在request作用域
		System.out.println(user);
		req.setAttribute("user", user);
		req.setAttribute("id", id);
		try {
			req.getRequestDispatcher("production/User_Info_table.jsp?id="+id+"").forward(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//查询所有
	private void findAllUser(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("进入了findall");
		List<Forge_Users>userList= service.findAll();
		for (Forge_Users forge_Users : userList) {
			System.out.println("++++++++++++++++++++++++"+userList);
		}
		//把集合存在session作用域中
		req.getSession().setAttribute("userList", userList);
		try {
			resp.sendRedirect("production/tables_dynamic.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		
	}
//新增用户
	private void addUser(HttpServletRequest req, HttpServletResponse resp) {
		
	}
	
}
