package com.numismaster.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.numismaster.model.Edge;

public class EdgeRepository {

    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public EdgeRepository() {
        factory = PersistenceManager.getEntityManagerFactory();
        entityManager = factory.createEntityManager();
    }

    public boolean insert(Edge edge) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(edge);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }

    public Edge findById(int id) {
        return entityManager.find(Edge.class, id);
    }

    public List<Edge> findAll() {
        return entityManager.createQuery("SELECT c FROM tb_edge c", Edge.class).getResultList();
    }

    public Edge findByName(String name) {
    	EntityManager em = factory.createEntityManager();
        try{
            Query query = em.createQuery("SELECT u FROM tb_edge u WHERE u.name = :name");
            query.setParameter("name", name);
            return (Edge) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean update(Edge edge) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(edge);
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
            Edge edge = entityManager.find(Edge.class, id);
            entityManager.remove(edge);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }
}