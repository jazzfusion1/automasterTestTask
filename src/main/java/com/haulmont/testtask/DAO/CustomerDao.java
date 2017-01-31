package com.haulmont.testtask.DAO;

import java.io.Serializable;
import java.util.List;


public interface CustomerDao<T, Id extends Serializable> {
    T findById(Id id);

    void delete(T entity);

    void saveOrUpdate(T entity);

    List<T> findAll();
}
