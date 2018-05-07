package com.forge.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;



/**
 * ���ﳵ
 * @author ����
 *
 */
public class Cart implements Serializable {
	//������һ�����ϱ������е���Ʒ//String ���� ��Ʒ��id
	private  static Map<String,CartItem>map=new LinkedHashMap();
	//������Ʒ���ܼ�
	private double price;
	private int count; 
	
	/**
	 * ������Ʒ�ķ���
	 */
	public void addProduct(Forge_Product product,int num){
		//��һ�ι���     ������϶�Ϊnull
		System.out.println("<<<<<<<<<<<<<<<<<<<<"+product.getId());
		CartItem cartItem=Cart.map.get(product.getId());
		System.out.println("map.get(product.getId()"+Cart.map.get(product.getId()));
		if(cartItem==null){//֤�����ﳵ��û���κ���Ʒ
			System.out.println("������϶�Ϊnull");
			cartItem=new CartItem();//ʵ����������
			//���û�����������Ʒ�Ž�������
			cartItem.setProduct(product);
			//��Ϊ�Ž����û��������Ʒ������0��Ϊ1
			cartItem.setNum(num);
			//�ѹ�����Ž����ﳵ 
			Cart.map.put(product.getId(), cartItem);
			System.out.println("cart====>map��Id"+Cart.map.get(product.getId()));
			System.out.println("cart====>map�ĳ���"+Cart.map.size());
		}else{
			System.out.println("������϶�Ϊ��null");
			//������ڸ���Ʒ   ����Ʒ������1
			cartItem.setNum(cartItem.getNum()+num);
			
		}
		
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = map.size();
	}

	public Cart(Map<String, CartItem> map, double price) {
		super();
		this.map = map;
		this.price = price;
	}

	public Cart() {
		super();
	}

	public Map<String, CartItem> getMap() {
		return map;
	}

	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}
//������Ʒ�ܼ�
	public double getPrice() {
		//��Ʒ���ܼ�
		double totalPrice=0;
		for (Entry<String,CartItem> product : map.entrySet()) {
			CartItem cartItem=product.getValue();
			totalPrice += cartItem.getPrice();
		}
		return totalPrice;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
}
