package com.numismaster.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.numismaster.model.Shape;

public class ShapeRepository {

    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public ShapeRepository() {
        factory = PersistenceManager.getEntityManagerFactory();
        entityManager = factory.createEntityManager();
    }

    public boolean insert(Shape shape) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(shape);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }

    public Shape findById(int id) {
        return entityManager.find(Shape.class, id);
    }
    
    public List<Shape> findAll() {
        return entityManager.createQuery("SELECT s FROM tb_shape s", Shape.class).getResultList();
    }

    public Shape findByName(String name) {
    	EntityManager em = factory.createEntityManager();
    	Query query = em.createQuery("SELECT u FROM tb_shape u WHERE u.name = :name");
    	query.setParameter("name", name);
    	return (Shape) query.getSingleResult();
    }
    
    public boolean update(Shape shape) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(shape);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }

    public boolean delete(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Shape shape = entityManager.find(Shape.class, id);
            entityManager.remove(shape);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }

}
