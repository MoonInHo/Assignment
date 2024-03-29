package com.innovation.assignment.product.infrastructure.repository;

import com.innovation.assignment.product.domain.entity.Product;
import com.innovation.assignment.product.domain.entity.QProduct;
import com.innovation.assignment.product.domain.enums.Category;
import com.innovation.assignment.product.domain.vo.ProductName;
import com.innovation.assignment.product.infrastructure.dto.response.GetProductResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Page<GetProductResponseDto> getProducts(Category category, Pageable pageable) {

        List<GetProductResponseDto> fetch = queryFactory
                .select(
                        Projections.fields(
                                GetProductResponseDto.class,
                                product.id,
                                product.productName.productName,
                                product.category,
                                product.quantity.quantity,
                                product.price.price,
                                product.productCode.productCode
                        )
                )
                .from(product)
                .where(isContainsCategory(category))
                .fetch();

        Long totalCount = Optional.ofNullable(
                queryFactory
                .select(product.count())
                .from(product)
                .where(isContainsCategory(category))
                .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(fetch, pageable, totalCount);
    }

    public Optional<GetProductResponseDto> getProduct(Long productId) {

        GetProductResponseDto result = queryFactory
                .select(
                        Projections.fields(
                                GetProductResponseDto.class,
                                product.id,
                                product.productName.productName,
                                product.category,
                                product.quantity.quantity,
                                product.price.price,
                                product.productCode.productCode
                        )
                )
                .from(product)
                .where(product.id.eq(productId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    private BooleanExpression isContainsCategory(Category category) {
        if (category == null) {
            return null;
        }
        return product.category.eq(category);
    }
}
