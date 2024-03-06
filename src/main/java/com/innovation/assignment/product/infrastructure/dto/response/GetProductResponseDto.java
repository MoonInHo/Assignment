package com.innovation.assignment.product.infrastructure.dto.response;

import com.innovation.assignment.product.domain.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductResponseDto {

    private Long id;
    private String productName;
    private Category category;
    private Integer quantity;
    private Integer price;
    private String productCode;
}
