package com.bootcamp.tugas1.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.bootcamp.tugas1.entities.Product;
import com.bootcamp.tugas1.repositories.ProductRepository;
import com.bootcamp.tugas1.utils.DBUtil;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProductService {
	@Inject
	protected ProductRepository repo;
	
	public List<Product> findAll() throws SQLException{
		List<Product> products =  this.repo.findAll();
		return products;
	}
	
	public Product findOneById(int productId) throws SQLException {
		Product product = this.repo.findOneById(productId);
		return product;
	}
	
	public int insert(Product product) throws SQLException {
		int result = this.repo.insertProduct(product);
//		if(result > 0) {
//			this.connection.commit();
//		}else {
//			this.connection.rollback();
//		}
//		this.connection.close();
		
		return result;
	}
	
	public int update(Product product, int productId) throws SQLException {
		int result = this.repo.updateProduct(product, productId);
		
//		if(result > 0) {
//			this.connection.commit();
//		}else {
//			this.connection.rollback();
//		}
//		this.connection.close();
		
		return result;
	}
	
	public int delete(int productId) throws SQLException {
		int result = this.repo.deleteProduct(productId);
		
//		if(result > 0) {
//			this.connection.commit();
//		}else {
//			this.connection.rollback();
//		}
//		this.connection.close();
//		
		return result;
	}
}
