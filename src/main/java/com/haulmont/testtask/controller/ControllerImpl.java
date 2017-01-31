package com.haulmont.testtask.controller;

import com.haulmont.testtask.DAO.entity.CustomerEntity;
import com.haulmont.testtask.DAO.entity.OrderEntity;
import com.haulmont.testtask.DAO.entity.SearchOrdersParams;
import com.haulmont.testtask.Model.Model;

import java.util.List;

/**
 * Created by Inferno on 13.12.2016.
 */
public class ControllerImpl implements Controller {
    private static ControllerImpl controller = null;

    @Override
    public List<CustomerEntity> findAllCustomers() {
        return Model.getInstance().getAllCustomersEntityList();
    }

    public static ControllerImpl getInstance() {
        if (controller == null) {
            controller = new ControllerImpl();
        }
        return controller;
    }

    @Override
    public List<OrderEntity> findAllOrders() {
        return Model.getInstance().getAllOrdersEntityList();
    }

    @Override
    public boolean deleteCustomer(CustomerEntity customerEntity) {
        return Model.getInstance().deleteCustomer(customerEntity);
    }

    @Override
    public void deleteOrder(OrderEntity orderEntity) {
        Model.getInstance().deleteOrder(orderEntity);
    }

    @Override
    public void saveOrUpdate(CustomerEntity customerEntity) {
        Model.getInstance().saveOrUpdate(customerEntity);
    }

    @Override
    public void saveOrUpdate(OrderEntity orderEntity) {
        Model.getInstance().saveOrUpdate(orderEntity);
    }

    @Override
    public List<OrderEntity> findOrdersByParams(SearchOrdersParams searchOrdersParams) {
        List<OrderEntity> resultList;
        if (searchOrdersParams == null) {
            resultList = findAllOrders();
        } else {
            resultList = Model.getInstance().findOrdersByParams(searchOrdersParams);
        }

        return resultList;
    }
}
