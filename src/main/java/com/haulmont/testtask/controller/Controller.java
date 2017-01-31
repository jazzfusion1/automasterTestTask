package com.haulmont.testtask.controller;

import com.haulmont.testtask.DAO.entity.CustomerEntity;
import com.haulmont.testtask.DAO.entity.OrderEntity;
import com.haulmont.testtask.DAO.entity.SearchOrdersParams;

import java.util.List;

/**
 * Created by Inferno on 13.12.2016.
 */
public interface Controller {
    public static Controller getInstance() {
        return ControllerImpl.getInstance();
    }

    List<CustomerEntity> findAllCustomers();

    List<OrderEntity> findAllOrders();

    boolean deleteCustomer(CustomerEntity customerEntity);

    void deleteOrder(OrderEntity orderEntity);

    void saveOrUpdate(CustomerEntity customerEntity);

    void saveOrUpdate(OrderEntity orderEntity);

    List<OrderEntity> findOrdersByParams(SearchOrdersParams searchOrdersParams);
}
