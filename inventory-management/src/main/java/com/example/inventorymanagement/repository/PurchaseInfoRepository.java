package com.example.inventorymanagement.repository;

import com.example.inventorymanagement.model.PurchaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseInfoRepository extends JpaRepository<PurchaseInfo, Long> {
}
