package com.forge.dao_impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.jboss.weld.bean.NewBean;

import com.forge.bean.Forge_News;
import com.forge.dao.Forge_News_Dao;
import com.forge.util.JdbcUtil;

public class Forge_News_Dao_Impl extends JdbcUtil implements Forge_News_Dao {

	@Override
	public int add(Forge_News t) {
		String sql="insert into forge_news(title,content,createTime,img)values(?,?,?,?)";
		Object[]params={t.getTitle(),t.getContent(),t.getCreateTime(),t.getImg()};
		//影响的行数
		int rowNum=0;
		try {
			rowNum=getmyExecuteUpdate(sql, params);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowNum;
	}

	@Override
	public int delete(Serializable id) {
		String sql="delete from forge_news where id=? ";
		Object[]params={id};
		int rowNum=0;
		try {
			rowNum=getmyExecuteUpdate(sql, params);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowNum;
	}

	@Override
	public int update(Forge_News t) {
		String sql="update from forge_news set loginName=?,password=? where id=? ";
	
		return 0;
	}

	@Override
	public List<Forge_News> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Forge_News findById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

}
