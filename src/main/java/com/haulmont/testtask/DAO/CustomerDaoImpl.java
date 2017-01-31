package com.haulmont.testtask.DAO;

import com.haulmont.testtask.DAO.entity.CustomerEntity;

import javax.persistence.EntityManager;
import java.util.List;


public class CustomerDaoImpl extends Dao implements CustomerDao<CustomerEntity, Long> {
    public CustomerEntity findById(Long id) {
        return getEntityManager().find(CustomerEntity.class, id);
    }

    public void delete(CustomerEntity entity) {
        super.delete(entity);
    }

    @Override
    public void saveOrUpdate(CustomerEntity entity) {
        super.saveOrUpdate(entity);
    }

    public List<CustomerEntity> findAll() {
        EntityManager entityManager = getEntityManager();
        List<CustomerEntity> list = entityManager
                .createQuery("from customer c order by c.name, c.surname, c.secname", CustomerEntity.class)
                .getResultList();
        entityManager.close();
        return list;
    }
}