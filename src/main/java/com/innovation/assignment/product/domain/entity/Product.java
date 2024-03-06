package com.innovation.assignment.product.domain.entity;

import com.innovation.assignment.product.domain.enums.Category;
import com.innovation.assignment.product.domain.vo.Price;
import com.innovation.assignment.product.domain.vo.ProductCode;
import com.innovation.assignment.product.domain.vo.ProductName;
import com.innovation.assignment.product.domain.vo.Quantity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(nullable = false, unique = true)
    private ProductName productName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Embedded
    @Column(nullable = false)
    private Quantity quantity;

    @Embedded
    @Column(nullable = false)
    private Price price;

    @Embedded
    @Column(nullable = false)
    private ProductCode productCode;

    private Product(
            ProductName productName,
            Category category,
            Quantity quantity,
            Price price
    ) {
        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.productCode = ProductCode.of(category);
    }

    public static Product createProduct(
            ProductName productName,
            Category category,
            Quantity quantity,
            Price price
    ) {
        return new Product(productName, category, quantity, price);
    }

    public void modifyProductInfo(ProductName productName, Category category, Quantity quantity, Price price) {
        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }
}
