package com.forge.servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.forge.bean.Forge_News;
import com.forge.service.Forge_News_Service;
import com.forge.service_impl.Forge_News_Service_Impl;

@WebServlet()
public class newsServlet extends HttpServlet {
//创建Forge_News_Service_Impl对象
	private Forge_News_Service service=new Forge_News_Service_Impl();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		super.doGet(req, resp);
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//设置post乱码
		req.setCharacterEncoding("utf-8");
		String method=req.getParameter("method");
		System.out.println("method");
		switch(method){
		case "add":
			add(req,resp);//新增方法
			break;
		case "delete":
			delete(req,resp);//删除方法
			break;
		case "update":
			update(req,resp);//修改方法
			break;
		
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
	private void update(HttpServletRequest req, HttpServletResponse resp) {
		//创建News对象
		Forge_News news=new Forge_News();
		System.out.println("临时文件存放的位置====》"+System.getProperty("java.io.tmpdir"));
		//创建factory对象  可以设置缓冲区大小  以及存放位置
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory);
		//判断是否是文件上传类型
		boolean flag=upload.isMultipartContent(req);
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
//						case "createTime":
//							news.setCreateTime(new SimpleDateFormat("dd/MM/yy")
//							.parse(item.getString("UTF-8")));
//							break;
						case "content":
							news.setContent(item.getString("UTF-8"));
							break;
						}
					}else{//证明是文件的元素
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
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							news.setImg(uploadPath + "\\" + fileName);// System.currentTimeMillis()
						}
					}
				}
			} catch (FileUploadException e) {
				
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}

	private void add(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}

}
