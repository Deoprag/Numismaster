package com.numismaster.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.numismaster.model.Coin;
import com.numismaster.model.Country;

public class CoinRepository {

    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public CoinRepository() {
        factory = PersistenceManager.getEntityManagerFactory();
        entityManager = factory.createEntityManager();
    }

    public boolean insert(Coin coin) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(coin);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public Coin findById(int id) {
        return entityManager.createQuery(
                "SELECT c FROM TB_Coin c ", Coin.class).setParameter("id", id).getSingleResult();
    }

    public Coin findByName(String name) {
        EntityManager em = factory.createEntityManager();
        Coin result;
        try {
            Query query = em.createQuery("SELECT c FROM TB_Coin c WHERE c.name = :name");
            query.setParameter("name", name);
            result = (Coin) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
        return result;
    }

    public List<Coin> findAll() {
        return entityManager.createQuery("SELECT c FROM TB_Coin c ", Coin.class).getResultList();
    }

    public boolean update(Coin coin) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(coin);
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
            Coin coin = entityManager.find(Coin.class, id);
            entityManager.remove(coin);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public List<Coin> findByCountry(Country country) {
        return entityManager.createQuery("SELECT c FROM TB_Coin c WHERE c.country = :country", Coin.class)
                .setParameter("country", country).getResultList();
    }
}