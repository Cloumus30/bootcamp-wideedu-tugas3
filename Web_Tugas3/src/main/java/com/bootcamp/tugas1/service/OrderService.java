package com.bootcamp.tugas1.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.bootcamp.tugas1.entities.Order;
import com.bootcamp.tugas1.repositories.OrderRepository;
import com.bootcamp.tugas1.utils.DBUtil;

public class OrderService {
	protected Connection connection;
	protected OrderRepository repo;
	
	public OrderService() throws NamingException, SQLException {
			
			this.connection = DBUtil.getConnection();
			this.connection.setAutoCommit(false);
			
			this.repo = new OrderRepository(this.connection);
	}
	
	public int insertOrder (Order order) throws SQLException {
		int result = 0;
		
		result = this.repo.insertOrder(order);
		
		if(result > 0) {
			this.connection.commit();
		}else {
			this.connection.rollback();
		}
		this.connection.close();
		
		return result;
	}
}
