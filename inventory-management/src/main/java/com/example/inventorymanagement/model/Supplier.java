package com.example.inventorymanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;



@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    @Column(name = "suppliercode")
    private String supplierCode;
    
    @Column(name = "fullname")
    private String fullName;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "phone")
    private String phone;

    // Constructors
    public Supplier() {
    }

    public Supplier(String supplierCode, String fullName, String location, String phone) {
        this.supplierCode = supplierCode;
        this.fullName = fullName;
        this.location = location;
        this.phone = phone;
    }

    // Getters and setters
    public Long getId() {
        return sid;
    }

    public void setId(Long id) {
        this.sid = id;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
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
