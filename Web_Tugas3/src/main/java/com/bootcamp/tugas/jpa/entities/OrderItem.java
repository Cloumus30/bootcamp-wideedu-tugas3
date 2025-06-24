package com.bootcamp.tugas.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "order_items")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "is_deleted")
	@NotNull
	private boolean isDeleted = false;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;
	
	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", productName=" + productName + ", type=" + type + ", price=" + price
				+ ", quantity=" + quantity + "]";
	}

	public OrderItem() {
		
	}
	
	public OrderItem(String productName, String type, double price, int quantity) {
		super();
		this.productName = productName;
		this.type = type;
		this.price = price;
		this.quantity = quantity;
	}
	
	public OrderItem(String productName, String type, double price, int quantity, Product product) {
		super();
		this.productName = productName;
		this.type = type;
		this.price = price;
		this.quantity = quantity;
		this.product = product;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
