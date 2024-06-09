package com.example.inventorymanagement.controller;

import com.example.inventorymanagement.model.SalesReport;
import com.example.inventorymanagement.model.User;
import com.example.inventorymanagement.model.Product;
import com.example.inventorymanagement.repository.SalesReportRepository;
import com.example.inventorymanagement.repository.UserRepository;
import com.example.inventorymanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salesreport")
public class SalesReportController {
    @Autowired
    private SalesReportRepository salesReportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<SalesReport> getAllSalesReports() {
        List<SalesReport> salesReportList = salesReportRepository.findAll();
        
        salesReportList.forEach(saleReport -> {
            User user = userRepository.findByUsername(saleReport.getSoldBy())
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + saleReport.getSoldBy()));
            saleReport.setFullname(user.getFullname());
            
            Product product = productRepository.findByProductCode(saleReport.getProductcode())
                    .orElseThrow(() -> new RuntimeException("Product not found with code: " + saleReport.getProductcode()));
            saleReport.setProductname(product.getProductName());
        });
        return salesReportList;
    }

    @PostMapping
    public SalesReport createSalesReport(@RequestBody SalesReport salesReport) {
        User user = userRepository.findByUsername(salesReport.getSoldBy())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + salesReport.getSoldBy()));

        salesReport.setFullname(user.getFullname());
        
        // Update product quantity
        Product product = productRepository.findByProductCode(salesReport.getProductcode())
                .orElseThrow(() -> new RuntimeException("Product not found with code: " + salesReport.getProductcode()));
        
        int newQuantity = product.getQuantity() - salesReport.getQuantity();
        if (newQuantity < 0) {
            throw new RuntimeException("Insufficient product quantity");
        }
        product.setQuantity(newQuantity);
        productRepository.save(product);

        return salesReportRepository.save(salesReport);
    }

    @GetMapping("/{id}")
    public SalesReport getSalesReportById(@PathVariable Long id) {
        return salesReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sales Report not found with id: " + id));
    }

    @PutMapping("/{id}")
    public SalesReport updateSalesReport(@PathVariable Long id, @RequestBody SalesReport updatedSalesReport) {
        return salesReportRepository.findById(id)
                .map(salesReport -> {
                    User user = userRepository.findByUsername(updatedSalesReport.getSoldBy())
                            .orElseThrow(() -> new RuntimeException("User not found with username: " + updatedSalesReport.getSoldBy()));


                    // Update product quantity
                    Product product = productRepository.findByProductCode(updatedSalesReport.getProductcode())
                            .orElseThrow(() -> new RuntimeException("Product not found with code: " + updatedSalesReport.getProductcode()));

                    int oldQuantity = salesReport.getQuantity();
                    int newQuantity = updatedSalesReport.getQuantity();
                    int quantityDifference = newQuantity - oldQuantity;
                    
                    
                    salesReport.setDate(updatedSalesReport.getDate());
                    salesReport.setCustomercode(updatedSalesReport.getCustomercode());
                    salesReport.setProductcode(updatedSalesReport.getProductcode());
                    salesReport.setQuantity(updatedSalesReport.getQuantity());
                    salesReport.setRevenue(updatedSalesReport.getRevenue());
                    salesReport.setSoldBy(user.getUsername());


                    if (product.getQuantity() < quantityDifference) {
                        throw new RuntimeException("Insufficient product quantity");
                    }

                    int updatedProductQuantity = product.getQuantity() - quantityDifference;
                    product.setQuantity(updatedProductQuantity);
                    productRepository.save(product);

                    return salesReportRepository.save(salesReport);
                })
                .orElseThrow(() -> new RuntimeException("Sales Report not found with id: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteSalesReport(@PathVariable Long id) {
        salesReportRepository.deleteById(id);
    }
}
