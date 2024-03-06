package com.innovation.assignment.product.infrastructure.repository;

import com.innovation.assignment.product.domain.entity.Product;
import com.innovation.assignment.product.domain.enums.Category;
import com.innovation.assignment.product.domain.vo.ProductName;
import com.innovation.assignment.product.infrastructure.dto.response.GetProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductQueryRepository {

    boolean isProductExist(ProductName productName);

    Page<GetProductResponseDto> getProducts(Category category, Pageable pageable);

    Optional<GetProductResponseDto> getProductInfo(Long productId);

    Optional<Product> getProduct(Long productId);
}
