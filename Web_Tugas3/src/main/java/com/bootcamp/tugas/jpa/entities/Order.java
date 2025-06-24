package com.bootcamp.tugas.jpa.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="customer_name")
	private String customerName;
	
	@Column(name="address")
	private String address;
	
	@Column(name="total")
	private double total;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	public List<OrderItem> getOrderItems() {
		return new ArrayList<OrderItem>(this.orderItems);
	}
	
	public void addOrderItem(OrderItem orderItem) {
		this.orderItems.add(orderItem);
	}
	
	public double countTotalPrice() {
		double totalPrice = 0;
		for (OrderItem orderItem : this.orderItems) {
			totalPrice += orderItem.getPrice() * orderItem.getQuantity();
		}
		this.total = totalPrice;
		return totalPrice;
	}

}
