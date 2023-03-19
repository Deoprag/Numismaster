package com.numismaster.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.numismaster.model.CoinUserSale;

public class CoinUserSaleRepository {

    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public CoinUserSaleRepository() {
        factory = PersistenceManager.getEntityManagerFactory();
        entityManager = factory.createEntityManager();
    }

    public boolean insert(CoinUserSale coinUserSale) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(coinUserSale);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public CoinUserSale findById(int id) {
        return entityManager.find(CoinUserSale.class, id);
    }

    public List<CoinUserSale> findAll() {
        return entityManager.createQuery("SELECT c FROM tb_coin_user_sale c", CoinUserSale.class).getResultList();
    }

    public boolean update(CoinUserSale coinUserSale) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(coinUserSale);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            CoinUserSale coinUserSale = entityManager.find(CoinUserSale.class, id);
            entityManager.remove(coinUserSale);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
}