package com.numismaster.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

import com.numismaster.model.Sale;

public class SaleRepository {

    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public SaleRepository() {
        factory = PersistenceManager.getEntityManagerFactory();
        entityManager = factory.createEntityManager();
    }

    public boolean insert(Sale sale) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(sale);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }

    public Sale findById(int id) {
        return entityManager.find(Sale.class, id);
    }

    public List<Sale> findAll() {
        return entityManager.createQuery("SELECT s FROM tb_sale s", Sale.class).getResultList();
    }

    public boolean update(Sale sale) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(sale);
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
            Sale sale = entityManager.find(Sale.class, id);
            entityManager.remove(sale);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }
}