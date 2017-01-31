package com.haulmont.testtask.enumerations;

/**
 * Created by Inferno on 10.01.2017.
 */
public enum OrderStatus {
    PLANNED("Запланирован", 0),
    EXECUTED("Выполнен", 1),
    ACCEPTED("Принят клиентом", 2);


    private Integer id;
    private String status;

    OrderStatus(String astatus, Integer aid) {
        id = aid;
        status = astatus;
    }

    public Integer getId() {
        return id;
    }

    public static OrderStatus fromId(Integer id) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getId().equals(id))
                return orderStatus;
        }
        return null;
    }

    @Override
    public String toString() {
        return status;
    }
}
