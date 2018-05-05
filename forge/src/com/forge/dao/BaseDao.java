package com.forge.dao;

import java.io.Serializable;
import java.util.List;

import com.forge.bean.Forge_Product_Category;



/**
 * 所有接口的公共接口
 * @author 郭阳
 *
 */
public interface BaseDao<T> {
/**
 * 新增
 * @param t
 * @return
 */
	int add(T t);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(Serializable id);
	/**
	 * 修改
	 * @param t
	 * @return
	 */
	int update(T t);
	/**
	 * 查询所有
	 * @return
	 */
	List<T>findAll();
	
	/**
	 * 根据id查询指定的信息
	 * @param id
	 * @return
	 */
	T findById(Serializable id);

}
