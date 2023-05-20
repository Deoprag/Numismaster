package com.numismaster.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.numismaster.model.CoinUser;

public class CoinUserRepository {

    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public CoinUserRepository() {
        factory = PersistenceManager.getEntityManagerFactory();
        entityManager = factory.createEntityManager();
    }

    public boolean insert(CoinUser coinUser) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(coinUser);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public CoinUser findById(int id) {
        return entityManager.find(CoinUser.class, id);
    }

    public List<CoinUser> findAll() {
        return entityManager.createQuery("SELECT c FROM tb_coin_user c", CoinUser.class).getResultList();
    }

    public List<CoinUser> findAllForSale() {
        return entityManager.createQuery("SELECT c FROM tb_coin_user c WHERE c.forSale = true", CoinUser.class).getResultList();
    }

    public List<CoinUser> findAllByUser(Long id) {
        String queryString = "SELECT c FROM tb_coin_user c WHERE c.user.id = :id";
        TypedQuery<CoinUser> query = entityManager.createQuery(queryString, CoinUser.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<CoinUser> findUsersByCoin(int id) {
        String queryString = "SELECT c FROM tb_coin_user c WHERE c.coin.id = :id";
        TypedQuery<CoinUser> query = entityManager.createQuery(queryString, CoinUser.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public boolean update(CoinUser coinUser) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(coinUser);
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
            CoinUser coinUser = entityManager.find(CoinUser.class, id);
            entityManager.remove(coinUser);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
}