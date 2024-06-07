package com.example.inventorymanagement.repository;

import com.example.inventorymanagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
