package com.innovation.assignment.product.domain.vo;

import com.innovation.assignment.product.domain.enums.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class ProductCode {

    private final String productCode;

    private ProductCode(String productCode) {
        this.productCode = productCode;
    }

    public static ProductCode of(Category category) {

        LocalDateTime currentDateTime = LocalDateTime.now();

        String formattedDateTime = currentDateTime
                .format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm:ss"));

        return new ProductCode(formattedDateTime + category.name());
    }
}
