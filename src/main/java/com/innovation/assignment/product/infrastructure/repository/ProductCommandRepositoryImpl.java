package com.innovation.assignment.product.infrastructure.repository;

import com.innovation.assignment.product.domain.enums.Category;
import com.innovation.assignment.product.domain.vo.Price;
import com.innovation.assignment.product.domain.vo.ProductName;
import com.innovation.assignment.product.domain.vo.Quantity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.innovation.assignment.product.domain.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductCommandRepositoryImpl implements ProductCommandRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public void modifyInfo(Long productId, ProductName productName, Category category, Quantity quantity, Price price) {
        queryFactory
                .update(product)
                .set(product.productName, productName)
                .set(product.category, category)
                .set(product.quantity, quantity)
                .set(product.price, price)
                .where(product.id.eq(productId))
                .execute();
    }
}
