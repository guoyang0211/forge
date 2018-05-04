package com.forge.bean;
/**
 * 购物项（购物车中的内容）
 * @author 郭阳
 *
 */
public class CartItem {
private Forge_Product product;//商品
private int num;//数量
private double price;//商品的总价
@Override
public String toString() {
	return "CartItem [product=" + product + ", num=" + num + ", price=" + price
			+ "]";
}
public CartItem() {
	super();
}
public CartItem(Forge_Product product, int num, double price) {
	super();
	this.product = product;
	this.num = num;
	this.price = price;
}
public Forge_Product getProduct() {
	return product;
}
public void setProduct(Forge_Product product) {
	this.product = product;
}
public int getNum() {
	return num;
}
public void setNum(int num) {
	this.num = num;
}
//商品的总价格  商品的单价*数量  返回给总价格
public double getPrice() {
	return product.getPrice()*num;
}
public void setPrice(double price) {
	this.price = price;
}
}
