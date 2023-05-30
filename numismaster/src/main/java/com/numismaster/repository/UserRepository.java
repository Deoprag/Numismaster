package com.numismaster.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.numismaster.model.User;
import com.numismaster.util.Util;

public class UserRepository {

    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public UserRepository() {
        factory = PersistenceManager.getEntityManagerFactory();
        entityManager = factory.createEntityManager();
    }

    public boolean insert(User user) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(user);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    public List<User> findAll() {
        return entityManager.createQuery("SELECT c FROM tb_user c", User.class).getResultList();
    }

    public boolean update(User user) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(user);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }

    public boolean delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            User user = entityManager.find(User.class, id);
            entityManager.remove(user);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }
    }

    public User login(String username, String password) {
        EntityManager em = factory.createEntityManager();
        User user = null;
        try {
            Query query = em.createQuery("SELECT u FROM tb_user u WHERE u.username = :username");
            query.setParameter("username", username);
            user = (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
        return user.getPassword().equals(Util.hashPassword(password)) ? user : null;
    }

    @SuppressWarnings("unchecked")
    public List<User> findAllByName(String name) {
        EntityManager em = factory.createEntityManager();
        Query query = em.createQuery("SELECT u FROM tb_user u WHERE u.firstName = :name OR u.lastName = :name");
        query.setParameter("name", name);
        return query.getResultList();
    }

    public User findByUsername(String username) {
        EntityManager em = factory.createEntityManager();
        User result;
        try {
            Query query = em.createQuery("SELECT u FROM tb_user u WHERE u.username = :username");
            query.setParameter("username", username);
            result = (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
        return result;
    }

    public User findByEmail(String email) {
        EntityManager em = factory.createEntityManager();
        User result;
        try {
            Query query = em.createQuery("SELECT u FROM tb_user u WHERE u.email = :email");
            query.setParameter("email", email);
            result = (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
        return result;
    }

    public User findByCpf(String cpf) {
        EntityManager em = factory.createEntityManager();
        User result;
        try {
            Query query = em.createQuery("SELECT u FROM tb_user u WHERE u.cpf = :cpf");
            query.setParameter("cpf", cpf);
            result = (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
        return result;
    }
}