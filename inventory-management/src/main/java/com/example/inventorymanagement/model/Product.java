package com.example.inventorymanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Column(name = "productcode", unique = true, nullable = false)
    private String productCode;
    
    @Column(name = "productname", nullable = false)
    private String productName;
    
    @Column(name = "costprice", nullable = false)
    private double costPrice;
    
    @Column(name = "sellingprice", nullable = false)
    private double sellingPrice;
    
    @Column(name = "brand", nullable = false)
    private String brand;

    // Constructors
    public Product() {
    }

    public Product(String productCode, String productName, double costPrice, double sellingPrice, String brand) {
        this.productCode = productCode;
        this.productName = productName;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.brand = brand;
    }

    // Getters and setters
    public Long getId() {
        return pid;
    }

    public void setId(Long id) {
        this.pid = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
