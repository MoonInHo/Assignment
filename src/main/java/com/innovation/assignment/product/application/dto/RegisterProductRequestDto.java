package com.innovation.assignment.product.application.dto;

import com.innovation.assignment.product.domain.entity.Product;
import com.innovation.assignment.product.domain.enums.Category;
import com.innovation.assignment.product.domain.vo.Price;
import com.innovation.assignment.product.domain.vo.ProductName;
import com.innovation.assignment.product.domain.vo.Quantity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterProductRequestDto {

    private String productName;
    private String category;
    private Integer quantity;
    private Integer price;

    public Product toEntity() {

        return Product.createProduct(
                ProductName.of(productName),
                Category.checkCategory(category),
                Quantity.of(quantity),
                Price.of(price)
        );
    }
}
