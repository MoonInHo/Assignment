package com.innovation.assignment.product.presentation.dto.request;

public record ModifyProductRequestDto(
        String productName,
        String category,
        Integer quantity,
        Integer price
) {
}
