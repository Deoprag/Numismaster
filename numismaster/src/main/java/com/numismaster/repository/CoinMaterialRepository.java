package com.numismaster.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.numismaster.model.CoinMaterial;
import com.numismaster.model.Material;

public class CoinMaterialRepository {

    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public CoinMaterialRepository() {
        factory = PersistenceManager.getEntityManagerFactory();
        entityManager = factory.createEntityManager();
    }

    public boolean insert(CoinMaterial coinMaterial) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(coinMaterial);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }

    public CoinMaterial findById(int id) {
        return entityManager.find(CoinMaterial.class, id);
    }

    public List<CoinMaterial> findAll() {
        return entityManager.createQuery("SELECT c FROM tb_coin_material c", CoinMaterial.class).getResultList();
    }

    public boolean update(CoinMaterial coinMaterial) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(coinMaterial);
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
            CoinMaterial coinMaterial = entityManager.find(CoinMaterial.class, id);
            entityManager.remove(coinMaterial);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public List<CoinMaterial> findCoinsByMaterial(Material material) {
        return entityManager
                .createQuery("SELECT c FROM tb_coin_material c WHERE c.material = :material", CoinMaterial.class)
                .setParameter("material", material).getResultList();
    }
}