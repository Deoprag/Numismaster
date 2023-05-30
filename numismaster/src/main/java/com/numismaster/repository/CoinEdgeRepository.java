package com.numismaster.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.numismaster.model.CoinEdge;
import com.numismaster.model.Edge;

public class CoinEdgeRepository {

    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public CoinEdgeRepository() {
        factory = PersistenceManager.getEntityManagerFactory();
        entityManager = factory.createEntityManager();
    }

    public boolean insert(CoinEdge coinEdge) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(coinEdge);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }

    public CoinEdge findById(int id) {
        return entityManager.find(CoinEdge.class, id);
    }

    public List<CoinEdge> findAll() {
        return entityManager.createQuery("SELECT c FROM tb_coin_edge c", CoinEdge.class).getResultList();
    }

    public boolean update(CoinEdge coinEdge) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(coinEdge);
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
            CoinEdge CoinEdge = entityManager.find(CoinEdge.class, id);
            entityManager.remove(CoinEdge);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public List<CoinEdge> findCoinsByEdge(Edge edge) {
        return entityManager.createQuery("SELECT c FROM tb_coin_edge c WHERE c.edge = :edge", CoinEdge.class)
                .setParameter("edge", edge).getResultList();
    }
}