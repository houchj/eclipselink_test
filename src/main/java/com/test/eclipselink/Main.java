package com.test.eclipselink;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Main {
	private static final String PERSISTENCE_UNIT_NAME = "todos";
	private static EntityManagerFactory factory;

	public static void main(String[] args) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		// read the existing entries and write to console
		Query q = em.createQuery("select t from Todo t");
		List<Todo> todoList = q.getResultList();
		for (Todo todo : todoList) {
			System.out.println(todo);
		}
		System.out.println("Size: " + todoList.size());

		// create new todo
		em.getTransaction().begin();
		Todo todo = new Todo();
		todo.setSummary("This is a test");
		todo.setDescription("This is a test");
		em.persist(todo);
		em.getTransaction().commit();

		//test for the product sku test case
		em.getTransaction().begin();
		Product p1 = new Product();
		p1.setCode("product1");		
		SKU s1 = new SKU();
		s1.setCode("sku1");
		s1.setProduct(p1);
		SKU s2 = new SKU();
		s2.setCode("sku2");
		s2.setProduct(p1);
		
		p1.getSkus().add(s1);
		p1.getSkus().add(s2);
		
		em.persist(p1);
		em.getTransaction().commit();
		
		em.close();
		
		
		em = factory.createEntityManager();
		q = em.createQuery("select t from SKU t");
		List<SKU> skus = q.getResultList();
		for (SKU sku : skus) {
			System.out.println(sku);
		}
		System.out.println("SKU Size: " + skus.size());
		
		em.close();
	}
}