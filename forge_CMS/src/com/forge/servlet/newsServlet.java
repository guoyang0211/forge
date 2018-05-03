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
//创建Forge_News_Service_Impl对象
	private Forge_News_Service service=new Forge_News_Service_Impl();

	
	
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
		case "method":
			add(req,resp);//新增方法
			break;
		case "delete":
			delete(req,resp);//删除方法
			break;
		case "update":
			update(req,resp);//修改方法
			break;
		case "findAll":
			findAllNews(req,resp);//查询所有
		case "findById":
			findById(req,resp);//根据id查询
			break;
	
		
		}
	}
	private void findById(HttpServletRequest req, HttpServletResponse resp) {
		String id = req.getParameter("id");
		Forge_News news=null;
		news = service.findById(id);
		// 保存在request作用域
	
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
	 * 新闻登录方法
	 * @param req
	 * @param resp
	 */


	//查询所有
	private void findAllNews(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("查询页面");
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
	 * 新增的方法  包含文件上传
	 * 01.引入需要的jar包
	 * 02.在form表中更改enctype
	 * 03.ServletFileUpload.isMultipartContent(request)  来判断我们的请求是不是文件上传请求
	 * 04.获取请求中所有的表单元素
	 * 		List<FileItem>  list=ServletFileUpload.parseRequest(request)
	 * 	 每一个表单元素就对应一个FileTtem
	 * 05.FileItem.isFormField()
	 * true===>普通的表单元素
	 *             getFiledName()===>获取name属性值
	 *             getString(String s)===》获取value的值  s===>编码格式
	 *     flase==>文件上传元素   
	 *            getName===>获取上传文件的名称
	 *            getContentType()===》获取上传文件的类型      mime-type
	 * @param req
	 * @param resp
	 * @throws FileUploadException 
	 */
	//修改
	private void update(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("进入了update");
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
			System.out.println("修改成功");
			try {
				resp.sendRedirect("/forge_CMS/NewsServlet?method=findAll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(null, "修改失败");
			System.out.println("修改失败");
			try {
				resp.sendRedirect("/forge_CMS/NewsServlet?method=findById&id="+id+"");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
	}
	//删除
	private void delete(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("进入了dele");
		String id=req.getParameter("id");
		int num = service.delete(Integer.valueOf(id));
		if(num!=0){
			System.out.println("删除成功");
			try {
				resp.sendRedirect("/forge_CMS/NewsServlet?method=findAll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("删除失败");
			try {
				resp.sendRedirect("/forge_CMS/NewsServlet?method=findAll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	//添加方法 
	private void add(HttpServletRequest req, HttpServletResponse resp) {
		//创建News对象
				Forge_News news=new Forge_News();
				System.out.println("临时文件存放的位置====》"+System.getProperty("java.io.tmpdir"));
				//创建factory对象  可以设置缓冲区大小  以及存放位置
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload=new ServletFileUpload(factory);
				//判断是否是文件上传类型
				boolean flag = upload.isMultipartContent(req);
				System.out.println(flag);
				if(flag){//form表单是文件上传类型
					try {
						
						List<FileItem> items=upload.parseRequest(req);
						//使用迭代器遍历  效率高
						Iterator<FileItem> its = items.iterator();
						while(its.hasNext()){
							FileItem item = its.next();
							//判断表单元素是什么类型
							if(item.isFormField()){//证明是普通表单元素
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
								//创建upload文件夹
								File file=new File(uploadPath);
								if(file.exists()){
									file.mkdirs();
								}
								String fileName = item.getName();//获取上传文件的名称
								fileName=new String(fileName.getBytes(),"utf-8");//解决中文乱码
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
					System.out.println("执行成功");
				}
		
	}

}
