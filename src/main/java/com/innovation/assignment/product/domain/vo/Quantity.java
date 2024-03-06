package com.innovation.assignment.product.domain.vo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Quantity {

    private final Integer quantity;

    private Quantity(Integer quantity) {
        this.quantity = quantity;
    }

    public static Quantity of(Integer quantity) {

        if (quantity == null) {
            throw new IllegalArgumentException("수량을 입력하세요.");
        }
        return new Quantity(quantity);
    }
}
