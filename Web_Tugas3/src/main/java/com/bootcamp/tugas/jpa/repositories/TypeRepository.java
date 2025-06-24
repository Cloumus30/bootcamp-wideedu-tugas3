package com.bootcamp.tugas.jpa.repositories;

import java.util.List;

import com.bootcamp.tugas.jpa.entities.Product;
import com.bootcamp.tugas.jpa.entities.Type;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class TypeRepository {
	@PersistenceContext
	private EntityManager em;
	
	public List<Type> findAll(){
		String query = "SELECT t FROM Type t";
		TypedQuery<Type> typedQuery = em.createQuery(query, Type.class);
		return typedQuery.getResultList();
	}
	
	public Type findById(int typeId) {
		String query = "SELECT t FROM Type t where t.id = :typeId";
		TypedQuery<Type> typedQuery = em.createQuery(query, Type.class);
		typedQuery.setParameter("typeId", typeId);
		return typedQuery.getSingleResult();
	}
	
	public void insertType(Type type) {
		em.persist(type);
	}
}
