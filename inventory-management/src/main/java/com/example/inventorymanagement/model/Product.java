package com.example.inventorymanagement.model;

import jakarta.persistence.*;

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
    
    @Column(name = "quantity", nullable = false)
    private int quantity;

    // Constructors
    public Product() {
    }

    public Product(String productCode, String productName, double costPrice, double sellingPrice, String brand, int quantity) {
        this.productCode = productCode;
        this.productName = productName;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.brand = brand;
        this.quantity = quantity;
    }

    // Getters and setters
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
