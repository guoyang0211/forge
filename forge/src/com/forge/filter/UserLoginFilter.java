package com.forge.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.forge.bean.Forge_Users;

public class UserLoginFilter implements Filter {

	@Override
	public void destroy() {
		
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;//子类的方法  向下转型
		HttpServletResponse resp=(HttpServletResponse) response;//子类的方法  向下转型
		String path = req.getRequestURI();//获取请求时的路径
		//Object类型进行下强转
		Forge_Users user = (Forge_Users) req.getSession().getAttribute("user");
		if(user!=null||path.contains("login")){
			//放行
			chain.doFilter(req, resp);
		}else{
			//回到login页面
			resp.sendRedirect("login.jsp");
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		
	}

}
