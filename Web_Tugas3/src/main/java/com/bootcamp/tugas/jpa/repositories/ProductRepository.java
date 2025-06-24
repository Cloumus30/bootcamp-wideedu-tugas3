package com.bootcamp.tugas.jpa.repositories;

import java.util.HashMap;
import java.util.List;

import com.bootcamp.tugas.jpa.entities.Product;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class ProductRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Product> findAll(){
		String query = "SELECT p FROM Product p WHERE p.isDeleted=0";
		TypedQuery<Product> typedQuery =  em.createQuery(query, Product.class);
		return typedQuery.getResultList();
	}
	
	public HashMap<Integer, Product> findAllWhereIn(List<Integer> productIds){
		String query = "SELECT p FROM Product p WHERE p.isDeleted=0 AND p.id IN :incList";
		TypedQuery<Product> typedQuery =  em.createQuery(query, Product.class);
		typedQuery.setParameter("incList", productIds);
		List<Product> products = typedQuery.getResultList();
		HashMap<Integer, Product> productsMap = new HashMap<Integer, Product>();
		for (Product product : products) {
			productsMap.put(product.getId(), product);
		}
		return productsMap;
	}
	
	public Product findById(int productId){
		String query = "SELECT p FROM Product p where p.id = :productId AND p.isDeleted=0";
		TypedQuery<Product> typedQuery =  em.createQuery(query, Product.class);
		typedQuery.setParameter("productId", productId);
		
		return typedQuery.getSingleResult();
	}
	
	public void insertProduct(Product product) {
		em.persist(product);
	}
	
	public void update(int productId, Product updatedProduct) {
		Product product = this.findById(productId);
		if(product != null) {
			updatedProduct.setId(productId);
			em.merge(updatedProduct);
		}
	}
	
	public void delete(int productId) {
		Product product = this.findById(productId);
		if(product != null) {
			product.setIsDeleted(true);
			em.merge(product);
		}
	}
}
