package com.forge.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.forge.bean.Forge_Product;
import com.forge.bean.Forge_Product_Category;
import com.forge.bean.Forge_UserTrack;
import com.forge.bean.Forge_Users;
import com.forge.bean.UserTrack;
import com.forge.dao.Forge_Product_Category_Dao;
import com.forge.service.Forge_Product_Category_Service;
import com.forge.service.Forge_Product_Service;
import com.forge.service.Forge_UserTrack_Service;
import com.forge.service_impl.Forge_Product_Category_Service_Impl;
import com.forge.service_impl.Forge_Product_Service_Impl;
import com.forge.service_impl.Forge_UserTrack_Service_Impl;
@WebServlet("/categoryServlet")
public class product_categoryServlet extends HttpServlet {
	Forge_Product_Category_Service service=new Forge_Product_Category_Service_Impl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String method = req.getParameter("method");
		System.out.println("jsp页面进来的method====》"+method);
		switch (method) {
		case "findAll":
			findAll(req,resp);
			break;
		case "findAll2":
			findAll2(req,resp);
			break;
		case "findAll3":
			findAll3(req,resp);
			break;
		case "findByT3":
			findByT3(req,resp);
			break;
		case "pageInfo":
			pageInfo(req,resp);
			break;
		case "books":
			findBooks(req,resp);
			break;
		case "queryTrack":
			queryTrack(req,resp);
			break;
		default:
			break;
		}
	}
	
	private void queryTrack(HttpServletRequest req, HttpServletResponse resp) {
		Forge_UserTrack_Service  ts = new Forge_UserTrack_Service_Impl(); 
		Forge_Users user = (Forge_Users) req.getSession().getAttribute("forgeUser");
		List <UserTrack> tracks = ts.queryTrack(user.getUserId());
		req.getSession().setAttribute("userTrack", tracks);
		try {
			resp.sendRedirect("my-track.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 模糊查询
	 */
	private void findBooks(HttpServletRequest req, HttpServletResponse resp) {
		//获取搜索框输入的内容
        String name=req.getParameter("name");
        /*try {
			name=new String(name.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}*/
        //向server层调用相应的业务
        Forge_Product_Service booksServer=new Forge_Product_Service_Impl();
        List<String> res=booksServer.findBooksAjax(name);
        String str="";
        for (int i = 0; i < res.size(); i++) {
        	if(i>0){
                str+=","+res.get(i);
            }else{
                str+=res.get(i);
            }
		}
        System.out.println("333"+str);
        //返回结果
        try {
			resp.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	

	
	
	
private void pageInfo(HttpServletRequest req, HttpServletResponse resp) {
	String id = req.getParameter("id");
	Forge_Users user = (Forge_Users) req.getSession().getAttribute("forgeUser");
	 
	//添加用户浏览记录
	addTrack(user.getUserId(),id,req,resp);
	req.getSession().setAttribute("pageid", id);
				try {
					resp.sendRedirect("page.jsp");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

private void addTrack(int userId, String id,HttpServletRequest req, HttpServletResponse resp) {
	System.out.println("进入了addTrack");
	Forge_UserTrack_Service track =new Forge_UserTrack_Service_Impl();
	Forge_Users user = (Forge_Users) req.getSession().getAttribute("forgeUser");
	
	if(null!=user){
		//查询数据库中用户的所有记录
		List <Forge_UserTrack> tracks = track.findByUserId(userId);   
		//标是否有相同的产品
		int count = 0; 
		//是否有用户浏览记录
		if(!tracks.isEmpty()){
			//循环遍历用户浏览记录，判断是否有相同的商品
			for(int i = 0;i<tracks.size();i++){
				if(Integer.valueOf(id)==tracks.get(i).getProductId()){
					count=count+1;
				}
			}
			//如果count大于0，说明有重复的商品 
			if(count>0){
				//先清空用户，看重复的商品，只时间更新，在添加
				System.out.println("进入了count》0");
				track.del(userId,Integer.valueOf(id));
				track.addTrack(userId,id);
			}else{
				System.out.println("进入了count《0"+count);
				track.addTrack(userId,id);
			}
		}else{
			System.out.println("没有浏览记录");
			track.addTrack(userId,id);

		}
		
		
	}
	
	
}


/**
 * 根据三级菜单的id获取下级商品
 * @param req
 * @param resp
 */
	private void findByT3(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("根据三级菜单的id获取下级商品");
		String id = req.getParameter("id");
		List<Forge_Product> products =service.findByT3(id);
		req.getSession().setAttribute("products", products);
		req.getSession().setAttribute("id", id);
		try {
			resp.sendRedirect("my-all.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/**
 * 查询三级菜单方法
 * @param req
 * @param resp
 */
	private void findAll3(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("进入了findAll3");
		List<Forge_Product_Category> findAll3 = service.findAll3();
		//把type3的存进session  用el表达式获取
		req.getSession().setAttribute("findAll3", findAll3);
		
	}

	/**
	 * 二级菜单
	 * @param req
	 * @param resp
	 */
	private void findAll2(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("进入了findAll2");
		List<Forge_Product_Category> list1 =  (List<Forge_Product_Category>) req.getSession().getAttribute("list");
		for (int i = 0; i < list1.size(); i++) {
			int id = list1.get(i).getId();
			List<Forge_Product_Category> list= service.findAll2(id);
			switch(id){
				case 548 :
					req.getSession().setAttribute("type21", list);
					System.out.println("================================="+list);
				break;
				case 628 :
					req.getSession().setAttribute("type22", list);
				break;
				case 660 :
					req.getSession().setAttribute("type23", list);
				break;
				case 670 :
					req.getSession().setAttribute("type24", list);
				break;
				case 676 :
					req.getSession().setAttribute("type25", list);
				break;
				case 681 :
					req.getSession().setAttribute("type26", list);
				break;
		}
		}
	
		
	}

	/**
	 * 一级菜单
	 * @throws IOException 
	 */
	private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("进入了findAll");
		List<Forge_Product_Category> list = service.findAll();
		
		req.getSession().setAttribute("list", list);
		
		PrintWriter writer;
		try {
			writer = resp.getWriter();
			writer.print("true");
		} catch (IOException e) {
			e.printStackTrace();
		}

		//直接加载二级菜单
		findAll2(req,resp);
		//直接加载三级菜单
		findAll3(req,resp);
		resp.sendRedirect("index2.jsp");
	}
	
	
	
	
	
	
	
	
	

}
