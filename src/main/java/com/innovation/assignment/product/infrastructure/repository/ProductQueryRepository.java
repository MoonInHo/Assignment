package com.innovation.assignment.product.infrastructure.repository;

import com.innovation.assignment.product.domain.vo.ProductName;

public interface ProductQueryRepository {

    boolean isProductExist(ProductName productName);
}
