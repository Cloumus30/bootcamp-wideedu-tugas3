package com.bootcamp.tugas1.entities;

public class OrderItem {
	private int id;
	private int productId;
	private String productName;
	private String type;
	private double price;
	private int quantity;
	
	public OrderItem(int productId, String productName, String type, double price, int quantity) {
		this.productId = productId;
		this.productName = productName;
		this.type = type;
		this.price = price;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderItem : {id=" + id + ", product_id=" + productId + ", product_name=" + productName + ", type="
				+ type + ", price=" + price + ", quantity=" + quantity + "}";
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getproductName() {
		return productName;
	}
	public void setproductName(String productName) {
		this.productName = productName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
