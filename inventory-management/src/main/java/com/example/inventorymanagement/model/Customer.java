package com.example.inventorymanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    @Column(name = "customercode")
    private String customerCode;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "location")
    private String location;

    @Column(name = "phone")
    private String phone;

    public Customer() {
    }

    public Customer(String customerCode, String fullName, String location, String phone) {
        this.customerCode = customerCode;
        this.fullName = fullName;
        this.location = location;
        this.phone = phone;
    }

    public Long getId() {
        return cid;
    }

    public void setId(Long id) {
        this.cid = id;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
