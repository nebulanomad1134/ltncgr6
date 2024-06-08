package com.example.inventorymanagement.controller;

import com.example.inventorymanagement.model.Product;
import com.example.inventorymanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
//        List<Product> ProductList = productRepository.findAll();
//        ProductList.forEach(product -> {
//            ProductStock productStock = productStockRepository.findByProductCode(product.getProductCode())
//                    .orElseThrow(() -> new RuntimeException("Product not found with code: " + product.getProductCode()));
//        product.setQuantity(productStock.getQuantity());
//        });
//        return ProductList;

    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
//        ProductStock productStock = new ProductStock(savedProduct.getProductCode(), savedProduct.getQuantity()); // Initialize stock with 0
//        productStockRepository.save(productStock);
        return savedProduct;
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setProductCode(updatedProduct.getProductCode());
                    product.setProductName(updatedProduct.getProductName());
                    product.setCostPrice(updatedProduct.getCostPrice());
                    product.setSellingPrice(updatedProduct.getSellingPrice());
                    product.setBrand(updatedProduct.getBrand());
                    product.setQuantity(updatedProduct.getQuantity()); // Update quantity directly
                
                return productRepository.save(product);
            })
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
}

                    
//                    // Update quantity
//                    ProductStock productStock = productStockRepository.findByProductCode(product.getProductCode())
//                            .orElseThrow(() -> new RuntimeException("Product stock not found for product code: " + product.getProductCode()));
//                    productStock.setQuantity(updatedProduct.getQuantity());
//                    productStockRepository.save(productStock);
//
//                    return productRepository.save(product);
//                })
//                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
//    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }

//    @GetMapping("/stock/{productCode}")
//    public ProductStock getProductStockByProductCode(@PathVariable String productCode) {
//        return productStockRepository.findByProductCode(productCode)
//                .orElseThrow(() -> new RuntimeException("Product stock not found with product code: " + productCode));
//    }
}
