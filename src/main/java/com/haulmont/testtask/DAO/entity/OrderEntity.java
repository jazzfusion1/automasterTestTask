package com.haulmont.testtask.DAO.entity;

import com.haulmont.testtask.enumerations.OrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Inferno on 10.01.2017.
 */
@Entity(name = "orders")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "ORDERS")
@Access(AccessType.PROPERTY)

public class OrderEntity implements Serializable, PersistObject {

    private Long id;
    private String desc;
    private CustomerEntity customer;
    private Date createDate;
    private Date endWorkDate;
    private BigDecimal cost;
    private Integer statusState;

    public OrderEntity() {
        this.createDate = new Date();
    }

    @SequenceGenerator(name = "orderSequence", sequenceName = "ORDER_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSequence")
    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "DESC")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @JoinColumn(name = "Customer")
    @ManyToOne(targetEntity = CustomerEntity.class, fetch = FetchType.EAGER)
    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    @Column(name = "createDate")
    @Temporal(TemporalType.DATE)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "endWorkDate")
    @Temporal(TemporalType.DATE)
    public Date getEndWorkDate() {
        return endWorkDate;
    }

    public void setEndWorkDate(Date endWorkDate) {
        this.endWorkDate = endWorkDate;
    }

    @Column(name = "cost")
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderEntity)) return false;

        OrderEntity that = (OrderEntity) o;

        if (!getId().equals(that.getId())) return false;
        if (getDesc() != null ? !getDesc().equals(that.getDesc()) : that.getDesc() != null) return false;
        if (!getCustomer().equals(that.getCustomer())) return false;
        if (getCreateDate() != null ? !getCreateDate().equals(that.getCreateDate()) : that.getCreateDate() != null)
            return false;
        if (getEndWorkDate() != null ? !getEndWorkDate().equals(that.getEndWorkDate()) : that.getEndWorkDate() != null)
            return false;
        if (getCost() != null ? !getCost().equals(that.getCost()) : that.getCost() != null) return false;
        return getStatusState().equals(that.getStatusState());
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", customer=" + customer +
                ", createDate=" + createDate +
                ", endWorkDate=" + endWorkDate +
                ", cost=" + cost +
                ", statusState=" + statusState +
                '}';
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + (getDesc() != null ? getDesc().hashCode() : 0);
        result = 31 * result + getCustomer().hashCode();
        result = 31 * result + (getCreateDate() != null ? getCreateDate().hashCode() : 0);
        result = 31 * result + (getEndWorkDate() != null ? getEndWorkDate().hashCode() : 0);
        result = 31 * result + (getCost() != null ? getCost().hashCode() : 0);
        result = 31 * result + getStatusState().hashCode();
        return result;
    }

    @Column(name = "STATUSSTATE")
    public Integer getStatusState() {
        return statusState;
    }

    public void setStatusState(Integer statusState) {
        this.statusState = statusState;
    }

    @Transient
    public OrderStatus getStatus() {
        return statusState == null ? null : OrderStatus.fromId(statusState);
    }

    public void setStatus(OrderStatus orderStatus) {
        this.statusState = orderStatus == null ? null : orderStatus.getId();
    }
}
