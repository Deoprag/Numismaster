package com.numismaster.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.numismaster.model.CoinShape;

public class CoinShapeRepository {

    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public CoinShapeRepository() {
        factory = PersistenceManager.getEntityManagerFactory();
        entityManager = factory.createEntityManager();
    }

    public boolean insert(CoinShape coinShape) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(coinShape);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }

    public CoinShape findById(int id) {
        return entityManager.find(CoinShape.class, id);
    }

    public List<CoinShape> findAll() {
        return entityManager.createQuery("SELECT c FROM tb_coin_shape c", CoinShape.class).getResultList();
    }

    public boolean update(CoinShape coinShape) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(coinShape);
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
            CoinShape coinShape = entityManager.find(CoinShape.class, id);
            entityManager.remove(coinShape);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }
}