package com.bootcamp.tugas1.entities;

import java.util.List;

public class Product {
	private int id;
	private String name;
	private double price;
	private Type type;
	private int typeId;
	
	
	
	public Product(int id, String name, double price, int typeId) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.typeId = typeId;
	}
	
	public Product(String name, double price, int typeId) {
		super();
		this.name = name;
		this.price = price;
		this.typeId = typeId;
	}
	
	public Product(int id, String name, double price, int typeId, Type type) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.typeId = typeId;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	
}
