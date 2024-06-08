package com.example.inventorymanagement.repository;

import com.example.inventorymanagement.model.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductStockRepository extends JpaRepository<ProductStock, String> {
    Optional<ProductStock> findByProductCode(String productCode);
}
