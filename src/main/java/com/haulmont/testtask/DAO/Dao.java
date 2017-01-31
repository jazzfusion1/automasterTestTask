package com.haulmont.testtask.DAO;

import com.haulmont.testtask.DAO.entity.PersistObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Inferno on 29.12.2016.
 */
abstract class Dao {
    private EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    private static EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("com.haulmont.testtask");

    protected EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public void saveOrUpdate(PersistObject entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void delete(PersistObject entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(entity.getClass(), entity.getId()));
        entityManager.getTransaction().commit();
        entityManager.close();
    }


}
