package com.innovation.assignment.product.infrastructure.repository;

import com.innovation.assignment.product.domain.enums.Category;
import com.innovation.assignment.product.domain.vo.ProductName;
import com.innovation.assignment.product.infrastructure.dto.response.GetProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryRepository {

    boolean isProductExist(ProductName productName);

    Page<GetProductResponseDto> getProductsInfo(Pageable pageable);

    Page<GetProductResponseDto> getProductsByCategory(Category category, Pageable pageable);
}
