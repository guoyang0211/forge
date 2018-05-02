package com.forge.service;

import java.io.Serializable;
import java.util.List;



public interface BaseServise<T> {
	/**
	 * 新增
	 * @param t
	 * @return
	 */
		void add(T t);
		/**
		 * 删除
		 * @param id
		 * @return
		 */
		void delete(Serializable id);
		/**
		 * 修改
		 * @param t
		 * @return
		 */
		void update(T t);
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
