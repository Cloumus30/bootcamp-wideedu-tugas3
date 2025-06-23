package com.bootcamp.tugas1.entities;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private int id;
	private String customerName;
	private String address;
	private double total;
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();
	
	public Order(String customerName, String address) {
		super();
		this.customerName = customerName;
		this.address = address;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void addOrderItem(OrderItem orderItem) {
		this.orderItems.add(orderItem);
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
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
	
	public double countTotalPrice() {
		double totalPrice = 0;
		for (OrderItem orderItem : this.orderItems) {
			totalPrice += orderItem.getPrice() * orderItem.getQuantity();
		}
		this.total = totalPrice;
		return totalPrice;
	}
}
