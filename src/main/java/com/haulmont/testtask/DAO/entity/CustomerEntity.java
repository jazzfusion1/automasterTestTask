package com.haulmont.testtask.DAO.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity(name = "customer")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "CUSTOMERS")
@Access(AccessType.PROPERTY)
public class CustomerEntity implements Serializable, PersistObject {

    @Override
    public String toString() {
        return name + " " + surname;
    }

    private Long id;
    private String name;
    private String surname;
    private String secname;
    private String phone;

    @SequenceGenerator(name = "customerSequence", sequenceName = "CUSTOMER_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerSequence")
    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "NAME", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "SURNAME", length = 100)
    public String getSurname() {
        return surname;
    }

    public void setSecname(String secname) {
        this.secname = secname;
    }

    @Column(name = "SECNAME", length = 100)
    public String getSecname() {
        return secname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "PHONE", nullable = false, length = 15)
    public String getPhone() {
        return phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerEntity)) return false;

        CustomerEntity that = (CustomerEntity) o;

        if (!getId().equals(that.getId())) return false;
        if (!getName().equals(that.getName())) return false;
        if (getSurname() != null ? !getSurname().equals(that.getSurname()) : that.getSurname() != null) return false;
        return getPhone().equals(that.getPhone());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + getPhone().hashCode();
        return result;
    }
}
