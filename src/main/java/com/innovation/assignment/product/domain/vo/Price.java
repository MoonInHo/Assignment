package com.innovation.assignment.product.domain.vo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Price {

    private final Integer price;

    private Price(Integer price) {
        this.price = price;
    }

    public static Price of(Integer price) {

        if (price == null) {
            throw new IllegalArgumentException("단가를 입력하세요.");
        }
        return new Price(price);
    }
}
