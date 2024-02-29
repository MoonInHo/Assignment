package com.innovation.assignment.customer.infrastructure.repository;

import com.innovation.assignment.customer.domain.vo.Password;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.innovation.assignment.customer.domain.entity.QCustomer.customer;

@Repository
@RequiredArgsConstructor
public class CustomerCommandRepositoryImpl implements CustomerCommandRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public void changePassword(Long memberId, Password password) {
        queryFactory
                .update(customer)
                .set(customer.password, password)
                .where(customer.id.eq(memberId))
                .execute();
    }
}
