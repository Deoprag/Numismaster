package com.numismaster.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.numismaster.model.Country;

public class CountryRepository {

    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public CountryRepository() {
        factory = PersistenceManager.getEntityManagerFactory();
        entityManager = factory.createEntityManager();
    }

    public boolean insert(Country country) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(country);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public Country findById(int id) {
        return entityManager.find(Country.class, id);
    }

    public Country findByName(String name) {
    	EntityManager em = factory.createEntityManager();
    	Query query = em.createQuery("SELECT u FROM tb_country u WHERE u.name = :name");
    	query.setParameter("name", name);
    	return (Country) query.getSingleResult();
    }

    public Country findByCode(String code) {
    	EntityManager em = factory.createEntityManager();
    	Query query = em.createQuery("SELECT u FROM tb_country u WHERE u.code = :code");
    	query.setParameter("code", code);
    	return (Country) query.getSingleResult();
    }

    public List<Country> findAll() {
        return entityManager.createQuery("SELECT c FROM tb_country c", Country.class).getResultList();
    }

    public boolean update(Country country) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(country);
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
            Country country = entityManager.find(Country.class, id);
            entityManager.remove(country);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
}