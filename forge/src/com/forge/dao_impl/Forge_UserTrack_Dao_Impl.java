package com.forge.dao_impl;

import java.sql.SQLException;
import java.util.List;

import com.forge.bean.Forge_UserTrack;
import com.forge.bean.UserTrack;
import com.forge.dao.Forge_UserTrack_Dao;
import com.forge.util.JdbcUtil;
import com.forge.util.ResultSerUtil;

public class Forge_UserTrack_Dao_Impl extends JdbcUtil implements Forge_UserTrack_Dao {


	@Override
	public void addTrack(int userId, String id) {
		String sql = "insert into forge_user_track (userId,productId) value (?,?)";
		//获取用户的浏览时间
		Object []param = {userId,id};
		try {
			getmyExecuteUpdate(sql, param);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Forge_UserTrack> findByUserId(int userId) {
		String sql = "select * from forge_user_track where userId=?";
		Object []param = {userId};
		List<Forge_UserTrack> tracks = null;
		try {
			rs =getmyExecuteQuery(sql, param);
			tracks = ResultSerUtil.findAll(rs,Forge_UserTrack.class);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tracks;
	}

	@Override
	public void del(int userId, int id) {
		String sql="DELETE  FROM forge_user_track WHERE userId=? AND productId=?";
		Object[]params={userId,id};
		try {
			getmyExecuteUpdate(sql, params);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/*@Override
	//查找浏览的所有商品
	public List<Forge_Product> findAll(Serializable userId) {
		ProductDao pdao = new ProductDaoImpl ();  //商品dao层实例，用于查询商品
		List<Forge_Product> products = new ArrayList();
		String sql = "SELECT * FROM`forge_user_tracks` WHERE userId = ?";
		Object []param = {userId};
		try {
			rs = myExcuteQuery(sql, param);
			//获取用户所有的
			List<Forge_Users_Tracks> tracks =  ResultSetUtil.findAll(rs ,Forge_Users_Tracks.class );
			//取出所有的商品Id
			for(int i = 0; i<tracks.size();i++){
				String productId = tracks.get(i).getProductId();
				Forge_Product product = pdao.findById(productId);
				products.add(product);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public void update(Serializable userId, String productId) {
				//获取用户的浏览时间
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				String date = df.format(new Date());// new Date()为获取当前系统时间
				String sql = "UPDATE forge_user_tracks SET viewTime=? WHERE userId=? AND productId=?";
				Object []param = {date,userId,productId};
				try {
					myExcuteUpdate(sql, param);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
*/
}
