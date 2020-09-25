package no.hvl.dat110.rest.counters;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class TodoDAO {
	
	private static final String PERSISTENCE_UNIT_NAME = "dat250";
    private static EntityManagerFactory factory;
    
    public TodoDAO() {
    	factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }
   
	public Todo getById(Long id) {
		EntityManager em = factory.createEntityManager();
		Todo todo = null;
		try {
			todo = em.find(Todo.class, id);
		} finally {
			em.close();
		}
		if (todo == null) {
			throw new EntityNotFoundException("Can't find todo with id " + id);
		}
		return todo;
	}

	public List<Todo> getAll() {
		EntityManager em = factory.createEntityManager();
		List<Todo> allTodos;
		try {
			Query q = em.createQuery("select t from Todo t");
			allTodos = q.getResultList();
		} finally {
			em.close();
		}
		return allTodos;
	}

	public void save(Todo t) {
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	public void update(Todo t) {
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(t);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	public void delete(Todo t) {
		EntityManager em = factory.createEntityManager();
		try {
			Todo toRemove = em.find(Todo.class, t.getId());
			em.getTransaction().begin();
			em.remove(toRemove);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
}
