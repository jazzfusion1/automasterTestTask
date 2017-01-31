package com.haulmont.testtask.DAO;

import com.haulmont.testtask.DAO.entity.CustomerEntity;
import com.haulmont.testtask.Model.Model;

import java.util.List;

public class CustomerService implements CustomerDao<CustomerEntity, Long> {
    private static CustomerDaoImpl customerDao;

    public CustomerService() {
        customerDao = new CustomerDaoImpl();
    }

    public void saveOrUpdate(CustomerEntity entity) {
        customerDao.saveOrUpdate(entity);
        Model.getInstance().notifyListeners();
    }


    public CustomerEntity findById(Long id) {
        return customerDao.findById(id);
    }


    public void delete(CustomerEntity Customer) {
        customerDao.delete(Customer);
        Model.getInstance().notifyListeners();
    }

    public List<CustomerEntity> findAll() {
        return customerDao.findAll();
    }

    public CustomerDaoImpl CustomerDao() {
        return customerDao;
    }
}