package com.forge_CMS.dao;

import java.io.Serializable;
import java.util.List;

import com.forge_CMS.bean.Forge_Users;



/**
 * ���нӿڵĹ����ӿ�
 * @author ����
 *
 */
public interface BaseDao<T> {
/**
 * ����
 * @param t
 * @return
 */
	int add(T t);
	/**
	 * ɾ��
	 * @param id
	 * @return
	 */
	int delete(Serializable id);
	/**
	 * �޸�
	 * @param t
	 * @return
	 */
	int update(Serializable id,T t);
	/**
	 * ��ѯ����
	 * @return
	 */
	List<T>findAll();
	
	/**
	 * ����id��ѯָ������Ϣ
	 * @param id
	 * @return
	 */
	T findById(Serializable id);
}
