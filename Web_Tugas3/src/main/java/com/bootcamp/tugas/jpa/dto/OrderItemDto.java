package com.bootcamp.tugas.jpa.dto;

import com.bootcamp.tugas.jpa.entities.Order;
import com.bootcamp.tugas.jpa.entities.Product;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class OrderItemDto {
	private int id;
	private String productName;
	private String type;
	private double price;
	private int quantity;
	private int productId;
	private int orderId;
	
	public OrderItemDto(String productName, String type, double price, int quantity, int productId,
			int orderId) {
		super();
		this.productName = productName;
		this.type = type;
		this.price = price;
		this.quantity = quantity;
		this.productId = productId;
		this.orderId = orderId;
	}
	
	public OrderItemDto(String productName, String type, double price, int quantity, int productId) {
		super();
		this.productName = productName;
		this.type = type;
		this.price = price;
		this.quantity = quantity;
		this.productId = productId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
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

	public int getproductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	
	
}
