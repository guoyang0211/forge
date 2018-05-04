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
			//����service��Ա����
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
		
		System.out.println("������Order_detals_Servlet");
		//��ȡmethod����
		String method = req.getParameter("method");
		System.out.println(method);
		
		switch (method) {
		case "findAll"://��ѯ���ж�������
			findAll(req,resp);
			break;
		case "findById"://����Id��ѯ
			findById(req,resp);
			break;
		case "update"://����Id��ѯ
			update(req,resp);
			break;
		case "delete"://����Id��ѯ
			delete(req,resp);
			break;

		default:
			break;
		}
		
		
		
	}
	private void delete(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("������delete����");
		String id = req.getParameter("id");
		int num = service.delete(id);
		if(num>0){
			System.out.println("ɾ���ɹ�");
			try {
				resp.sendRedirect("Order_detals_Servlet?method=findAll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("ɾ��ʧ��");
			try {
				resp.sendRedirect("Order_detals_Servlet?method=findAll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void update(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("������update");
		String id = req.getParameter("id");
		//��ȡ�û�����Ķ�������
		String quantity = req.getParameter("quantity");
		System.out.println("============================================"+quantity);
		System.out.println(Integer.parseInt(quantity));
		//��String���͵�quantityת����int������set��detail
		//detail.setQuantity(Integer.parseInt(quantity));
		detail = new Forge_Order_Detail(id,Integer.parseInt(quantity));
		//����service����
		boolean update = service.update(id, detail);
		if(update){
			System.out.println("�޸ĳɹ�");
			try {
				resp.sendRedirect("Order_detals_Servlet?method=findAll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("�޸�ʧ��");
			try {
				resp.sendRedirect("Order_detals_Servlet?method=findAll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * ����id��ѯ����
	 * @param req
	 * @param resp
	 */
private void findById(HttpServletRequest req, HttpServletResponse resp) {
		//��ȡid
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
 * ��ѯ���ж������鷽��
 * @param req
 * @param resp
 */
	private void findAll(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("������findall����");
		//������������ʵ����
		Forge_Order_Detail detail=new Forge_Order_Detail();
		//����service�㷽������һ������  ��ѯ����
		List<Forge_Order_Detail> findAll = service.findAll();
		//�Ѽ��ϱ�����session��
		req.getSession().setAttribute("list", findAll);
		try {
			resp.sendRedirect("production/tables_order_detals_sdynamic.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	
}
