package com.numismaster.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

import com.numismaster.model.Material;

public class MaterialRepository {

    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public MaterialRepository() {
        factory = PersistenceManager.getEntityManagerFactory();
        entityManager = factory.createEntityManager();
    }

    public boolean insert(Material material) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(material);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }

    public Material findById(int id) {
        return entityManager.find(Material.class, id);
    }

    public List<Material> findAll() {
        return entityManager.createQuery("SELECT m FROM tb_material m", Material.class).getResultList();
    }
    
    public boolean update(Material material) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(material);
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
            Material material = entityManager.find(Material.class, id);
            entityManager.remove(material);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }

}