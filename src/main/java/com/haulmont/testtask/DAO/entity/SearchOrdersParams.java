package com.haulmont.testtask.DAO.entity;

import com.haulmont.testtask.enumerations.OrderStatus;

import javax.persistence.MappedSuperclass;

/**
 * Created by Inferno on 21.01.2017.
 */
@MappedSuperclass
public class SearchOrdersParams {
    private CustomerEntity customer;
    private OrderStatus status;
    private String descPart;

    public SearchOrdersParams() {
    }

    public SearchOrdersParams(CustomerEntity customer) {
        this.customer = customer;
    }

    // @Transient
    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getDescPart() {
        return "".equals(this.descPart) ? null : descPart;
    }

    public void setDescPart(String descPart) {
        this.descPart = descPart;
    }

    @Override
    public String toString() {
        return "SearchOrdersParams{" +
                "customer=" + customer +
                ", status=" + status +
                ", descPart='" + getDescPart() + '\'' +
                '}';
    }
}
