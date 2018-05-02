package com.forge.util;
import java.io.IOException;
import java.net.InetSocketAddress;
import net.spy.memcached.MemcachedClient;



	/**
	 * 1.在电脑中安装指定版本的memcached
	 * 2.在项目中引入需要的jar
	 * 3.在工具包中创建memcached的工具类 方便service层使用
	 * 4.在service层写入逻辑 即可
	 * @author 郭阳
	 *
	 */
public class MemcachedUtil {
	//01.私有化本类对象
	private static MemcachedUtil util;
	private static MemcachedClient client=null;
	static{
		util=new MemcachedUtil();
		try {
			client=new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	//02.私有化构造
	private MemcachedUtil(){
		
	}
	//03.创建对外访问的接口
	public static MemcachedClient getInstance(){
		return client;
	}
}

