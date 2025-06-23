package com.bootcamp.tugas1.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bootcamp.tugas1.entities.Type;

public class TypeRepository {
	protected Connection connection;
	
	
	public TypeRepository(Connection connection) {
		this.connection = connection;
		try {
			this.connection.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Type> findAll() throws SQLException{
		List<Type> types = new ArrayList<Type>();
		String query = "SELECT * FROM types";
		
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()) {
				Type type = new Type(result.getInt("id"), result.getString("name"));
				types.add(type);
			}
		this.connection.close();
		return types;
	}
}
