package com.forge.servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.forge_CMS.bean.Forge_News;
import com.forge_CMS.bean.Forge_Users;

import Forge_News_service.Forge_News_Service;
import Forge_News_service_Impl.Forge_News_Service_Impl;



@WebServlet("/NewsServlet")
public class newsServlet extends HttpServlet {
//����Forge_News_Service_Impl����
	private Forge_News_Service service=new Forge_News_Service_Impl();

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//����post����
		req.setCharacterEncoding("utf-8");
		String method=req.getParameter("method");

		System.out.println(method);
	
		
		switch(method){
		case "method":
			add(req,resp);//��������
			break;
		case "delete":
			delete(req,resp);//ɾ������
			break;
		case "update":
			update(req,resp);//�޸ķ���
			break;
		case "findAll":
			findAllNews(req,resp);//��ѯ����
		case "findById":
			findById(req,resp);//����id��ѯ
			break;
	
		
		}
	}
	private void findById(HttpServletRequest req, HttpServletResponse resp) {
		String id = req.getParameter("id");
		Forge_News news=null;
		news = service.findById(id);
		// ������request������
	
		req.getSession().setAttribute("news", news);
		//req.setAttribute("id", id);
	
			try {
				resp.sendRedirect("production/News_Info_table.jsp?id="+id+"");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//req.getRequestDispatcher("production/News_Info_table.jsp?id="+id+"").forward(req, resp);
		
				
				//req.getRequestDispatcher("production/News_Info_table.jsp?id="+id+"").forward(req, resp);
			
		
		
	}

	/**
	 * ���ŵ�¼����
	 * @param req
	 * @param resp
	 */


	//��ѯ����
	private void findAllNews(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("��ѯҳ��");
		List<Forge_News> newsList = service.findAll();
	
		req.getSession().setAttribute("newsList", newsList);
		//req.setAttribute("newsList", newsList);
		
		//req.getRequestDispatcher("/production/tables_newsdynamic.jsp").forward(req, resp);
		try {
			resp.sendRedirect("production/tables_newsdynamic.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
	}

	/**
	 * �����ķ���  �����ļ��ϴ�
	 * 01.������Ҫ��jar��
	 * 02.��form���и���enctype
	 * 03.ServletFileUpload.isMultipartContent(request)  ���ж����ǵ������ǲ����ļ��ϴ�����
	 * 04.��ȡ���������еı�Ԫ��
	 * 		List<FileItem>  list=ServletFileUpload.parseRequest(request)
	 * 	 ÿһ����Ԫ�ؾͶ�Ӧһ��FileTtem
	 * 05.FileItem.isFormField()
	 * true===>��ͨ�ı�Ԫ��
	 *             getFiledName()===>��ȡname����ֵ
	 *             getString(String s)===����ȡvalue��ֵ  s===>�����ʽ
	 *     flase==>�ļ��ϴ�Ԫ��   
	 *            getName===>��ȡ�ϴ��ļ�������
	 *            getContentType()===����ȡ�ϴ��ļ�������      mime-type
	 * @param req
	 * @param resp
	 * @throws FileUploadException 
	 */
	//�޸�
	private void update(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("������update");
		String id = req.getParameter("id");
		String title =req.getParameter("title");
		String time=req.getParameter("time");
		String content=req.getParameter("content");
		
		System.out.println("============================"+id);
		Forge_News news=new Forge_News();
		news.setId(Integer.valueOf(id));
		news.setTitle(title);
		;
		try {
			news.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").parse(title));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		news.setContent(content);
		
		
		
		boolean update = service.update(id,news);
		if(update){
			System.out.println("�޸ĳɹ�");
			try {
				resp.sendRedirect("/forge_CMS/NewsServlet?method=findAll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(null, "�޸�ʧ��");
			System.out.println("�޸�ʧ��");
			try {
				resp.sendRedirect("/forge_CMS/NewsServlet?method=findById&id="+id+"");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
	}
	//ɾ��
	private void delete(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("������dele");
		String id=req.getParameter("id");
		int num = service.delete(Integer.valueOf(id));
		if(num!=0){
			System.out.println("ɾ���ɹ�");
			try {
				resp.sendRedirect("/forge_CMS/NewsServlet?method=findAll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("ɾ��ʧ��");
			try {
				resp.sendRedirect("/forge_CMS/NewsServlet?method=findAll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	//��ӷ��� 
	private void add(HttpServletRequest req, HttpServletResponse resp) {
		//����News����
				Forge_News news=new Forge_News();
				System.out.println("��ʱ�ļ���ŵ�λ��====��"+System.getProperty("java.io.tmpdir"));
				//����factory����  �������û�������С  �Լ����λ��
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload=new ServletFileUpload(factory);
				//�ж��Ƿ����ļ��ϴ�����
				boolean flag = upload.isMultipartContent(req);
				System.out.println(flag);
				if(flag){//form�����ļ��ϴ�����
					try {
						
						List<FileItem> items=upload.parseRequest(req);
						//ʹ�õ���������  Ч�ʸ�
						Iterator<FileItem> its = items.iterator();
						while(its.hasNext()){
							FileItem item = its.next();
							//�жϱ�Ԫ����ʲô����
							if(item.isFormField()){//֤������ͨ��Ԫ��
								String fieldName = item.getFieldName();// title context
																		//createTime
								switch(fieldName){
								case "title":
									news.setTitle(item.getString("UTF-8"));
									break;
								case "createTime":
									try {
										news.setCreateTime(new SimpleDateFormat("dd/MM/yyyy")
										.parse(item.getString("UTF-8")));
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									break;
								case "content":
									news.setContent(item.getString("UTF-8"));
									break;
								}
								
							}else{
								String uploadPath=req.getSession().getServletContext().getRealPath("upload/");
								//����upload�ļ���
								File file=new File(uploadPath);
								if(file.exists()){
									file.mkdirs();
								}
								String fileName = item.getName();//��ȡ�ϴ��ļ�������
								fileName=new String(fileName.getBytes(),"utf-8");//�����������
								if(!"".equals(fileName)&&null!=fileName){
									File saveFile = new File(uploadPath, fileName);
									try {
										item.write(saveFile);
									} catch (Exception e) {
										e.printStackTrace();
									}
									news.setImg(uploadPath + "\\" + fileName);// System.currentTimeMillis()
								}
							}
						}
					} catch (FileUploadException e) {
						
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				
					service.add(news);
					System.out.println("ִ�гɹ�");
				}
		
	}

}
