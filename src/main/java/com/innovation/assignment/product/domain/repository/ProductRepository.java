package com.innovation.assignment.product.domain.repository;

import com.innovation.assignment.product.domain.entity.Product;
import com.innovation.assignment.product.infrastructure.repository.ProductQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductQueryRepository {
}
