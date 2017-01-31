package com.haulmont.testtask.DAO;

import com.haulmont.testtask.DAO.entity.SearchOrdersParams;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Inferno on 10.01.2017.
 */
public interface OrderDao<T, Id extends Serializable> {
    T findById(Id id);

    List<T> findByParams(SearchOrdersParams params);

    void delete(T entity);

    List<T> findAll();

    void saveOrUpdate(T entity);
}
