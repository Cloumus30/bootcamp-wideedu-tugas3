package com.bootcamp.tugas1.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.bootcamp.tugas1.entities.Type;
import com.bootcamp.tugas1.repositories.TypeRepository;
import com.bootcamp.tugas1.utils.DBUtil;

public class TypeService {
	protected Connection connection;
	
	public TypeService() throws NamingException, SQLException {
		
		this.connection = DBUtil.getConnection();
	}
	
	public List<Type> findAll() throws SQLException{
		TypeRepository repo = new TypeRepository(this.connection);
		return repo.findAll();
	}
}
