package com.innovation.assignment.product.infrastructure.repository;

import com.innovation.assignment.product.domain.vo.ProductName;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.innovation.assignment.product.domain.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean isProductExist(ProductName productName) {
        return queryFactory
                .selectOne()
                .from(product)
                .where(product.productName.eq(productName))
                .fetchFirst() != null;
    }
}
