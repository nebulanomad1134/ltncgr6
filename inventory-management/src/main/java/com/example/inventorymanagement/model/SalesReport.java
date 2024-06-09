package com.example.inventorymanagement.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "salesreport")
public class SalesReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salesid")
    private Long salesid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd")
    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "customercode", nullable = false)
    private String customercode;

    @Column(name = "productcode", nullable = false)
    private String productcode;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "revenue", nullable = false)
    private double revenue;

    @Column(name = "soldby", nullable = false)
    private String soldby;
    
    @Transient
    private String fullname;
    
    @Transient
    private String productname;


    // Constructors
    public SalesReport() {}

    public SalesReport(Date date, String customercode, String productcode, int quantity, double revenue, String soldby) {
        this.date = date;
        this.customercode = customercode;
        this.productcode = productcode;
        this.quantity = quantity;
        this.revenue = revenue;
        this.soldby = soldby;
    }

    // Getters and setters
    public Long getSalesid() {
        return salesid;
    }

    public void setSalesid(Long salesid) {
        this.salesid = salesid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public String getSoldBy() {
        return soldby;
    }

    public void setSoldBy(String soldby) {
        this.soldby = soldby;
    }
    
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    
    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }
}

