package com.innovation.assignment.customer.infrastructure.repository;

import com.innovation.assignment.customer.domain.entity.Customer;
import com.innovation.assignment.customer.domain.entity.QCustomer;
import com.innovation.assignment.customer.domain.vo.Email;
import com.innovation.assignment.customer.domain.vo.Phone;
import com.innovation.assignment.customer.infrastructure.dto.GetCustomerResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.innovation.assignment.customer.domain.entity.QCustomer.customer;

@Repository
@RequiredArgsConstructor
public class CustomerQueryRepositoryImpl implements CustomerQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean isEmailExist(Email email) {
        return queryFactory
                .selectOne()
                .from(customer)
                .where(customer.email.eq(email))
                .fetchFirst() != null;
    }

    @Override
    public boolean isPhoneExist(Phone phone) {
        return queryFactory
                .selectOne()
                .from(customer)
                .where(customer.phone.eq(phone))
                .fetchFirst() != null;
    }

    @Override
    public Page<GetCustomerResponseDto> getCustomers(Pageable pageable) {
        List<GetCustomerResponseDto> fetch = queryFactory
                .select(
                        Projections.fields(
                                GetCustomerResponseDto.class,
                                customer.id,
                                customer.email.email,
                                customer.password.password,
                                customer.birthDate.birthDate,
                                customer.phone.phone,
                                customer.address.address,
                                customer.address.addressDetail
                        )
                )
                .from(customer)
                .orderBy(customer.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = Optional.ofNullable(
                queryFactory
                        .select(customer.count())
                        .from(customer)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(fetch, pageable, totalCount);
    }

    @Override
    public Optional<GetCustomerResponseDto> getCustomerByEmail(Email email) {

        GetCustomerResponseDto result = queryFactory
                .select(
                        Projections.fields(
                                GetCustomerResponseDto.class,
                                customer.id,
                                customer.email.email,
                                customer.password.password,
                                customer.birthDate.birthDate,
                                customer.phone.phone,
                                customer.address.address,
                                customer.address.addressDetail
                        )
                )
                .from(customer)
                .where(customer.email.eq(email))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<GetCustomerResponseDto> getCustomerByPhone(Phone phone) {

        GetCustomerResponseDto result = queryFactory
                .select(
                        Projections.fields(
                                GetCustomerResponseDto.class,
                                customer.id,
                                customer.email.email,
                                customer.password.password,
                                customer.birthDate.birthDate,
                                customer.phone.phone,
                                customer.address.address,
                                customer.address.addressDetail
                        )
                )
                .from(customer)
                .where(customer.phone.eq(phone))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Customer> getCustomer(Long customerId) {

        Customer result = queryFactory
                .selectFrom(QCustomer.customer)
                .where(QCustomer.customer.id.eq(customerId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
