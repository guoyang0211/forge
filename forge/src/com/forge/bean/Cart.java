package com.forge.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;



/**
 * 购物车
 * @author 郭阳
 *
 */
public class Cart implements Serializable {
	//必须有一个集合保存所有的商品//String 就是 商品的id
	private  static Map<String,CartItem>map=new LinkedHashMap();
	//所有商品的总价
	private double price;
	private int count; 
	
	/**
	 * 新增商品的方法
	 */
	public void addProduct(Forge_Product product,int num){
		//第一次购物     购物项肯定为null
		System.out.println("<<<<<<<<<<<<<<<<<<<<"+product.getId());
		CartItem cartItem=Cart.map.get(product.getId());
		System.out.println("map.get(product.getId()"+Cart.map.get(product.getId()));
		if(cartItem==null){//证明购物车中没有任何商品
			System.out.println("购物项肯定为null");
			cartItem=new CartItem();//实例化购物项
			//将用户传进来的商品放进购物项
			cartItem.setProduct(product);
			//因为放进来用户传入的商品数量从0变为1
			cartItem.setNum(num);
			//把购物项放进购物车 
			Cart.map.put(product.getId(), cartItem);
			System.out.println("cart====>map的Id"+Cart.map.get(product.getId()));
			System.out.println("cart====>map的长度"+Cart.map.size());
		}else{
			System.out.println("购物项肯定为不null");
			//如果存在该商品   该商品数量加1
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
//所有商品总价
	public double getPrice() {
		//商品的总价
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
