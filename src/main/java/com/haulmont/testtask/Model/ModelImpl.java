package com.haulmont.testtask.Model;

import com.haulmont.testtask.DAO.CustomerService;
import com.haulmont.testtask.DAO.OrderSevice;
import com.haulmont.testtask.DAO.entity.CustomerEntity;
import com.haulmont.testtask.DAO.entity.OrderEntity;
import com.haulmont.testtask.DAO.entity.SearchOrdersParams;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Inferno on 09.01.2017.
 */
public class ModelImpl implements Model {
    private static HashSet<ModelChangeListnener> modelChangeListneners = new HashSet<ModelChangeListnener>();
    private static CustomerService customerService = new CustomerService();
    private static OrderSevice orderSevice = new OrderSevice();
    private static ModelImpl instance = null;

    @Override
    public boolean deleteCustomer(CustomerEntity customerEntity) {
        List<OrderEntity> list = orderSevice.findByParams(new SearchOrdersParams(customerEntity));
        if (list.isEmpty())
            customerService.delete(customerEntity);
        return list.isEmpty();
    }

    @Override
    public void saveOrUpdate(OrderEntity orderEntity) {
        orderSevice.saveOrUpdate(orderEntity);
    }

    @Override
    public void addChangeListener(ModelChangeListnener listnener) {
        modelChangeListneners.add(listnener);
    }

    @Override
    public List<CustomerEntity> getAllCustomersEntityList() {
        List<CustomerEntity> customerEntities = new ArrayList<>();
        customerService.findAll().forEach(customerEntities::add);
        return customerEntities;
    }

    @Override
    public List<OrderEntity> getAllOrdersEntityList() {
        List<OrderEntity> orderEntities = new ArrayList<>();
        orderSevice.findAll().forEach(orderEntities::add);
        return orderEntities;
    }

    public void notifyListeners() {
        modelChangeListneners.forEach(ModelChangeListnener::modelChanged);
    }

    @Override
    public void saveOrUpdate(CustomerEntity customerEntity) {
        customerService.saveOrUpdate(customerEntity);
    }

    @Override
    public List<OrderEntity> findOrdersByParams(SearchOrdersParams searchOrdersParams) {
        return orderSevice.findByParams(searchOrdersParams);
    }

    @Override
    public void deleteOrder(OrderEntity orderEntity) {
        orderSevice.delete(orderEntity);
    }

    public static ModelImpl getInstance() {
        if (instance == null) {
            instance = new ModelImpl();
        }
        return instance;
    }
}
