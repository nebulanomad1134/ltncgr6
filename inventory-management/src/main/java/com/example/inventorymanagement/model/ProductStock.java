package com.example.inventorymanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "productstock")
public class ProductStock {
    @Id
    @Column(name = "productcode")
    private String productCode;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    // Constructors
    public ProductStock() {}

    public ProductStock(String productCode, int quantity) {
        this.productCode = productCode;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
