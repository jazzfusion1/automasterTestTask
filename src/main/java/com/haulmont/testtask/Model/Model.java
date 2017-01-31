package com.haulmont.testtask.Model;

import com.haulmont.testtask.DAO.entity.CustomerEntity;
import com.haulmont.testtask.DAO.entity.OrderEntity;
import com.haulmont.testtask.DAO.entity.SearchOrdersParams;

import java.util.List;

/**
 * Created by Inferno on 13.12.2016.
 */
public interface Model {
    boolean deleteCustomer(CustomerEntity customerEntity);

    void saveOrUpdate(OrderEntity orderEntity);

    void saveOrUpdate(CustomerEntity customerEntity);

    List<OrderEntity> findOrdersByParams(SearchOrdersParams searchOrdersParams);

    void deleteOrder(OrderEntity orderEntity);

    interface ModelChangeListnener {
        void modelChanged();
    }

    void addChangeListener(ModelChangeListnener listnener);

    List<CustomerEntity> getAllCustomersEntityList();

    List<OrderEntity> getAllOrdersEntityList();

    void notifyListeners();

    public static Model getInstance() {
        return ModelImpl.getInstance();
    }
}
