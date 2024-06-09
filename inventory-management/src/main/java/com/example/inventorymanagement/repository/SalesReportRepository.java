package com.example.inventorymanagement.repository;

import com.example.inventorymanagement.model.SalesReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesReportRepository extends JpaRepository<SalesReport, Long> {
}
