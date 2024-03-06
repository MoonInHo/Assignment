package com.innovation.assignment.product.domain.vo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class ProductName {

    private final String productName;

    private ProductName(String productName) {
        this.productName = productName;
    }

    public static ProductName of(String productName) {

        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("상품명을 입력하세요.");
        }
        return new ProductName(productName);
    }
}
