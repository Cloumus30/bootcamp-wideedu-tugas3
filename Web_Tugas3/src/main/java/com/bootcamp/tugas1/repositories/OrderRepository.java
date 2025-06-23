package com.bootcamp.tugas1.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bootcamp.tugas1.entities.Order;
import com.bootcamp.tugas1.entities.OrderItem;

public class OrderRepository {
	protected Connection connection;
	
	public OrderRepository(Connection connection){
		this.connection = connection;
	}
	
	public int insertOrder(Order order) throws SQLException {
		int result = 0;
		int orderId = 0;
		String orderQuery = "INSERT INTO orders (customer_name, address, total) VALUES (? , ?, ?)";
		String orderItemQuery = "INSERT INTO order_items (product_id, product_name, type, price, quantity) VALUES (?, ?, ?, ?, ?)";
		
		PreparedStatement preparedStatementOrder = this.connection.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
		preparedStatementOrder.setString(1, order.getCustomerName());
		preparedStatementOrder.setString(2, order.getAddress());
		preparedStatementOrder.setDouble(3, order.countTotalPrice());
		result = preparedStatementOrder.executeUpdate();
		
//		Get Order Id
		ResultSet keygen = preparedStatementOrder.getGeneratedKeys();
		while(keygen.next()) {
			orderId = keygen.getInt(1);
		}
		
		if(result > 0) {
			PreparedStatement preparedStatementOrderItems = this.connection.prepareStatement(orderItemQuery);
			for (OrderItem orderItem : order.getOrderItems()) {
				preparedStatementOrderItems.setInt(1, orderId);
				preparedStatementOrderItems.setString(2, orderItem.getproductName());
				preparedStatementOrderItems.setString(3, orderItem.getType());
				preparedStatementOrderItems.setDouble(4, orderItem.getPrice());
				preparedStatementOrderItems.setInt(5, orderItem.getQuantity());
				preparedStatementOrderItems.addBatch();
			}
			int[] res =preparedStatementOrderItems.executeBatch();
			result = 1;
			for (int i : res) {
				if (i<1) {
					result = 0;
				}
			}
		}
		
		
		return result;
	}
}
