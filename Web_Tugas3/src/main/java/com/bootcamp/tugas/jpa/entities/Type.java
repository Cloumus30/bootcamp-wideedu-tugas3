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
@Table(name = "types")
public class Type {
	@Override
	public String toString() {
		return "Type {id=" + id + ", name=" + name + ", products=" + products + "}";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private List<Product> products = new ArrayList<Product>();

	public Type() {
		
	}
	
	public Type(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addProducts(Product product) {
		this.products.add(product);
	}

	public int getId() {
		return id;
	} 
	
	public List<Product> getProducts(){
		return new ArrayList<Product>(this.products);
	}
}
