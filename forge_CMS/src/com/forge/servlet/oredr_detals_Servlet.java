package com.forge.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Forge_News_service.Forge_Order_Detail_Service;
import Forge_News_service_Impl.Forge_Order_Detail_Service_Impl;

import com.forge_CMS.bean.Forge_Order_Detail;
@WebServlet("/Order_detals_Servlet")
public class oredr_detals_Servlet extends HttpServlet {
			//创建service成员变量
			Forge_Order_Detail_Service service=new Forge_Order_Detail_Service_Impl();
			Forge_Order_Detail detail=null;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("进入了Order_detals_Servlet");
		//获取method方法
		String method = req.getParameter("method");
		System.out.println(method);
		
		switch (method) {
		case "findAll"://查询所有订单详情
			findAll(req,resp);
			break;
		case "findById"://根据Id查询
			findById(req,resp);
			break;
		case "update"://根据Id查询
			update(req,resp);
			break;
		case "delete"://根据Id查询
			delete(req,resp);
			break;

		default:
			break;
		}
		
		
		
	}
	private void delete(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("进入了delete方法");
		String id = req.getParameter("id");
		int num = service.delete(id);
		if(num>0){
			System.out.println("删除成功");
			try {
				resp.sendRedirect("Order_detals_Servlet?method=findAll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("删除失败");
			try {
				resp.sendRedirect("Order_detals_Servlet?method=findAll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void update(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("进入了update");
		String id = req.getParameter("id");
		//获取用户输入的订单数量
		String quantity = req.getParameter("quantity");
		System.out.println("============================================"+quantity);
		System.out.println(Integer.parseInt(quantity));
		//把String类型的quantity转换成int类型在set到detail
		//detail.setQuantity(Integer.parseInt(quantity));
		detail = new Forge_Order_Detail(id,Integer.parseInt(quantity));
		//调用service方法
		boolean update = service.update(id, detail);
		if(update){
			System.out.println("修改成功");
			try {
				resp.sendRedirect("Order_detals_Servlet?method=findAll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("修改失败");
			try {
				resp.sendRedirect("Order_detals_Servlet?method=findAll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * 根据id查询方法
	 * @param req
	 * @param resp
	 */
private void findById(HttpServletRequest req, HttpServletResponse resp) {
		//获取id
	String id = req.getParameter("id");
	Forge_Order_Detail detail=null;
	detail = service.findById(Integer.valueOf(id));
	
	req.getSession().setAttribute("detail", detail);
	//req.setAttribute("id", id);

		try {
			resp.sendRedirect("production/order_detail_Info_table.jsp?id="+id+"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	
	}

/**
 * 查询所有订单详情方法
 * @param req
 * @param resp
 */
	private void findAll(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("进入了findall方法");
		//创建订单详情实体类
		Forge_Order_Detail detail=new Forge_Order_Detail();
		//调用service层方法返回一个集合  查询所有
		List<Forge_Order_Detail> findAll = service.findAll();
		//把集合保存在session中
		req.getSession().setAttribute("list", findAll);
		try {
			resp.sendRedirect("production/tables_order_detals_sdynamic.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	
}
