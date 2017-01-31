package com.haulmont.testtask.DAO;

import com.haulmont.testtask.DAO.entity.OrderEntity;
import com.haulmont.testtask.DAO.entity.SearchOrdersParams;
import com.haulmont.testtask.Model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inferno on 13.01.2017.
 */
public class OrderSevice implements OrderDao<OrderEntity,Long>{
    private static OrderDaoImpl orderDao =  new OrderDaoImpl();

    @Override
    public OrderEntity findById(Long aLong) {
        return orderDao.findById(aLong);
    }

    @Override
    public List<OrderEntity> findByParams(SearchOrdersParams params) {
        return orderDao.findByParams(params);
    }

    @Override
    public void delete(OrderEntity entity) {
        orderDao.delete(entity);
        Model.getInstance().notifyListeners();
    }

    @Override
    public List<OrderEntity> findAll() {
        return orderDao.findAll();
    }

    @Override
    public void saveOrUpdate(OrderEntity entity) {
        orderDao.saveOrUpdate(entity);
        Model.getInstance().notifyListeners();
    }
}
