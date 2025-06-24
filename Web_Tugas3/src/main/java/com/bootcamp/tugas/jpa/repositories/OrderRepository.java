package com.bootcamp.tugas.jpa.repositories;

import java.util.List;

import com.bootcamp.tugas.jpa.entities.Order;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class OrderRepository {
	@PersistenceContext
	private EntityManager em;
	
	public List<Order> findAll(){
		String query = "SELECT o FROM Order o where o.isDeleted = 0";
		TypedQuery<Order> typedOrder = em.createQuery(query, Order.class);
		
		return typedOrder.getResultList();
	}
	
	public void insert(Order order) {
		System.out.println(order);
		System.out.println("besok");
		em.persist(order);
	}
	
}
