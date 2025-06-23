package com.bootcamp.tugas1.repositories;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bootcamp.tugas1.entities.Product;
import com.bootcamp.tugas1.entities.Type;

public class ProductRepository {
	
	protected Connection connection;
	
	public ProductRepository(Connection connection){
		this.connection = connection;
	}
	
	public List<Product> findAll() throws SQLException{
		List<Product> products = new ArrayList<Product>();
		String query = "select p.id as product_id, p.name as product_name, p.type_id, p.price as product_price, t.name as type_name from products p\r\n"
				+ "left join types t on t.id = p.type_id where p.is_deleted=0";
		
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			ResultSet result = preparedStatement.executeQuery();
			
			while (result.next()) {
				Type type = new Type(result.getInt("type_id"), result.getString("type_name"));
				Product product = new Product(result.getInt("product_id"), result.getString("product_name"), result.getDouble("product_price"), type.getId(), type);
				
				products.add(product);
			}
		this.connection.close();
		return products;
	}
	
	public Product findOneById(int productId) throws SQLException {
		String query = "select p.id as product_id, p.name as product_name, p.type_id, p.price as product_price, t.name as type_name from products p\r\n"
				+ "left join types t on t.id = p.type_id where p.id = ? and p.is_deleted=0";
		Product product = null;
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setInt(1, productId);
		ResultSet result = preparedStatement.executeQuery();
		
		if(result.next()) {
			Type type = new Type(result.getInt("type_id"), result.getString("type_name"));
			product = new Product(result.getInt("product_id"), result.getString("product_name"), result.getDouble("product_price"), type.getId(), type);
		}
		
		return product;
	}
	
	public int insertProduct(Product product) throws SQLException {
		int result = 0;
		String query = "INSERT INTO products (name, type_id, price) VALUES (?, ?, ?)";
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setString(1, product.getName());
		preparedStatement.setInt(2, product.getTypeId());
		preparedStatement.setDouble(3, product.getPrice());
		result = preparedStatement.executeUpdate();
		
		
		return result;
	}
	
	public int updateProduct (Product product, int productId) throws SQLException {
		int result = 0;
		String query = "UPDATE products SET name = ?, type_id = ?, price = ? where id = ? and is_deleted=0";
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setString(1, product.getName());
		preparedStatement.setInt(2, product.getTypeId());
		preparedStatement.setDouble(3, product.getPrice());
		preparedStatement.setInt(4, productId);
		result = preparedStatement.executeUpdate();
		
		return result;
	}
	
	public int deleteProduct(int productId) throws SQLException {
		int result = 0;
		String query = "UPDATE products SET is_deleted=1 where id = ?";
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setInt(1, productId);
		result = preparedStatement.executeUpdate();
		
		return result;
	}
	
}
