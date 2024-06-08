package com.example.inventorymanagement.controller;

import com.example.inventorymanagement.model.PurchaseInfo;
import com.example.inventorymanagement.model.Supplier;
import com.example.inventorymanagement.model.Product;
import com.example.inventorymanagement.repository.PurchaseInfoRepository;
import com.example.inventorymanagement.repository.SupplierRepository;
import com.example.inventorymanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchaseinfo")
public class PurchaseInfoController {

    @Autowired
    private PurchaseInfoRepository purchaseInfoRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<PurchaseInfo> getAllPurchaseInfo() {
        List<PurchaseInfo> purchaseInfoList = purchaseInfoRepository.findAll();
        purchaseInfoList.forEach(purchaseInfo -> {
            Supplier supplier = supplierRepository.findBySupplierCode(purchaseInfo.getSupplierCode())
                    .orElseThrow(() -> new RuntimeException("Supplier not found with code: " + purchaseInfo.getSupplierCode()));
            Product product = productRepository.findByProductCode(purchaseInfo.getProductCode())
                    .orElseThrow(() -> new RuntimeException("Product not found with code: " + purchaseInfo.getProductCode()));
            purchaseInfo.setSupplierName(supplier.getFullName());
            purchaseInfo.setProductName(product.getProductName());
        });
        return purchaseInfoList;
    }

    @PostMapping
    public PurchaseInfo createPurchaseInfo(@RequestBody PurchaseInfo purchaseInfo) {
        Supplier supplier = supplierRepository.findBySupplierCode(purchaseInfo.getSupplierCode())
                .orElseThrow(() -> new RuntimeException("Supplier not found with code: " + purchaseInfo.getSupplierCode()));
        Product product = productRepository.findByProductCode(purchaseInfo.getProductCode())
                .orElseThrow(() -> new RuntimeException("Product not found with code: " + purchaseInfo.getProductCode()));

        purchaseInfo.setSupplierName(supplier.getFullName());
        purchaseInfo.setProductName(product.getProductName());

        // Update product quantity
        int newQuantity = product.getQuantity() + purchaseInfo.getQuantity();
        product.setQuantity(newQuantity);
        productRepository.save(product);

        return purchaseInfoRepository.save(purchaseInfo);
    }

    @GetMapping("/{id}")
    public PurchaseInfo getPurchaseInfoById(@PathVariable Long id) {
        PurchaseInfo purchaseInfo = purchaseInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Info not found with id: " + id));
        Supplier supplier = supplierRepository.findBySupplierCode(purchaseInfo.getSupplierCode())
                .orElseThrow(() -> new RuntimeException("Supplier not found with code: " + purchaseInfo.getSupplierCode()));
        Product product = productRepository.findByProductCode(purchaseInfo.getProductCode())
                .orElseThrow(() -> new RuntimeException("Product not found with code: " + purchaseInfo.getProductCode()));
        purchaseInfo.setSupplierName(supplier.getFullName());
        purchaseInfo.setProductName(product.getProductName());
        return purchaseInfo;
    }

    @PutMapping("/{id}")
    public PurchaseInfo updatePurchaseInfo(@PathVariable Long id, @RequestBody PurchaseInfo updatedPurchaseInfo) {
        return purchaseInfoRepository.findById(id)
                .map(purchaseInfo -> {
                    Supplier supplier = supplierRepository.findBySupplierCode(updatedPurchaseInfo.getSupplierCode())
                            .orElseThrow(() -> new RuntimeException("Supplier not found with code: " + updatedPurchaseInfo.getSupplierCode()));
                    Product product = productRepository.findByProductCode(updatedPurchaseInfo.getProductCode())
                            .orElseThrow(() -> new RuntimeException("Product not found with code: " + updatedPurchaseInfo.getProductCode()));

                    int oldQuantity = purchaseInfo.getQuantity();
                    int newQuantity = updatedPurchaseInfo.getQuantity();
                    int quantityDifference = newQuantity - oldQuantity;

                    purchaseInfo.setSupplierCode(updatedPurchaseInfo.getSupplierCode());
                    purchaseInfo.setProductCode(updatedPurchaseInfo.getProductCode());
                    purchaseInfo.setDate(updatedPurchaseInfo.getDate());
                    purchaseInfo.setQuantity(newQuantity);
                    purchaseInfo.setTotalCost(updatedPurchaseInfo.getTotalCost());
                    purchaseInfo.setSupplierName(supplier.getFullName());
                    purchaseInfo.setProductName(product.getProductName());

                    // Update product quantity based on the difference
                    int updatedProductQuantity = product.getQuantity() + quantityDifference;
                    product.setQuantity(updatedProductQuantity);
                    productRepository.save(product);

                    return purchaseInfoRepository.save(purchaseInfo);
                })
                .orElseThrow(() -> new RuntimeException("Purchase Info not found with id: " + id));
    }

    @DeleteMapping("/{id}")
    public void deletePurchaseInfo(@PathVariable Long id) {
        purchaseInfoRepository.deleteById(id);
    }
}
