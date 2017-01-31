package com.haulmont.testtask.DAO;

import com.haulmont.testtask.DAO.entity.OrderEntity;
import com.haulmont.testtask.DAO.entity.SearchOrdersParams;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inferno on 10.01.2017.
 */

public class OrderDaoImpl extends Dao implements OrderDao<OrderEntity, Long> {

    @Override
    public OrderEntity findById(Long id) {
        EntityManager entityManager = getEntityManager();
        OrderEntity entity = entityManager.find(OrderEntity.class, id);
        entityManager.close();
        return entity;
    }

    @Override
    public List<OrderEntity> findByParams(SearchOrdersParams params) {
        EntityManager entityManager = getEntityManager();
        List<Predicate> predicates = new ArrayList<Predicate>();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> cq = cb.createQuery(OrderEntity.class);
        Root<OrderEntity> entityRoot = cq.from(OrderEntity.class);
        cq.select(entityRoot);
        if (params.getCustomer() != null) {
            predicates.add(cb.equal(entityRoot.get("customer"), params.getCustomer()));
        }
        if (params.getStatus() != null) {
            predicates.add(cb.equal(entityRoot.get("statusState"), params.getStatus().getId()));
        }
        if (params.getDescPart() != null) {
            String descPartEsc = params.getDescPart().replaceAll("[\\\\]+", "\\\\\\\\");
            descPartEsc = descPartEsc.replaceAll("[%]+", "\\\\%");
            descPartEsc = descPartEsc.replaceAll("[_]+", "\\\\_");
            descPartEsc = descPartEsc.toLowerCase();

            predicates.add(cb.like(cb.lower(entityRoot.get("desc")), "%" + descPartEsc + "%", '\\'));
        }
        cq.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<OrderEntity> query = entityManager.createQuery(cq);
        List<OrderEntity> list = query.getResultList();
        entityManager.close();
        return list;
    }

    @Override
    public void delete(OrderEntity entity) {
        super.delete(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<OrderEntity> findAll() {
        EntityManager entityManager = getEntityManager();
        List<OrderEntity> list =        entityManager.createQuery("from orders").getResultList();
        entityManager.close();
        return list;
    }

    @Override
    public void saveOrUpdate(OrderEntity entity) {
        super.saveOrUpdate(entity);
    }
}
