package com.innovation.assignment.product.infrastructure.repository;

import com.innovation.assignment.product.domain.enums.Category;
import com.innovation.assignment.product.domain.vo.Price;
import com.innovation.assignment.product.domain.vo.ProductName;
import com.innovation.assignment.product.domain.vo.Quantity;

public interface ProductCommandRepository {

    void modifyInfo(Long productId, ProductName productName, Category category, Quantity quantity, Price price);
}
