package com.numismaster.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.numismaster.model.UserRequest;

public class UserRequestRepository {
    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public UserRequestRepository() {
        factory = PersistenceManager.getEntityManagerFactory();
        entityManager = factory.createEntityManager();
    }

    public boolean insert(UserRequest userRequest) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(userRequest);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }

    public UserRequest findById(int id) {
        return entityManager.find(UserRequest.class, id);
    }

    public List<UserRequest> findAll() {
        return entityManager.createQuery("SELECT r FROM tb_user_request r", UserRequest.class).getResultList();
    }

    public boolean update(UserRequest userRequest) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(userRequest);
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
            UserRequest userRequest = entityManager.find(UserRequest.class, id);
            entityManager.remove(userRequest);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            ex.printStackTrace();
            return false;
        }
    }
}
