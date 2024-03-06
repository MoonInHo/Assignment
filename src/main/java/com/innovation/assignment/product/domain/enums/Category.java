package com.innovation.assignment.product.domain.enums;

import java.util.Arrays;

public enum Category {

    CATEGORY1("카테고리1"),
    CATEGORY2("카테고리2");

    private final String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public static Category checkCategory(String categoryName) {
        return Arrays.stream(Category.values())
                .filter(ct -> ct.categoryName.equals(categoryName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품 분류입니다."));
    }
}
