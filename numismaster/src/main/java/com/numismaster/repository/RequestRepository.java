package com.numismaster.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.numismaster.model.Request;

public class RequestRepository {

    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public RequestRepository() {
        factory = PersistenceManager.getEntityManagerFactory();
        entityManager = factory.createEntityManager();
    }

    public boolean insert(Request request) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(request);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }

    public Request findById(int id) {
        return entityManager.find(Request.class, id);
    }

    public List<Request> findAll() {
        return entityManager.createQuery("SELECT r FROM tb_request r", Request.class).getResultList();
    }

    public boolean update(Request request) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(request);
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
            Request request = entityManager.find(Request.class, id);
            entityManager.remove(request);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            ex.printStackTrace();
            return false;
        }
    }
}
