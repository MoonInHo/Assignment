package com.innovation.assignment.customer.infrastructure.repository;

import com.innovation.assignment.customer.domain.vo.Address;
import com.innovation.assignment.customer.domain.vo.BirthDate;
import com.innovation.assignment.customer.domain.vo.Password;
import com.innovation.assignment.customer.domain.vo.Phone;
import com.innovation.assignment.customer.presentation.dto.request.ChangeCustomerInfoRequestDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.innovation.assignment.customer.domain.entity.QCustomer.customer;

@Repository
@RequiredArgsConstructor
public class CustomerCommandRepositoryImpl implements CustomerCommandRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public void changePassword(Long customerId, Password password) {
        queryFactory
                .update(customer)
                .set(customer.password, password)
                .where(customer.id.eq(customerId))
                .execute();
    }

    @Override
    public void modifyDetails(Long customerId, BirthDate birthDate, Phone phone, Address address) {
        queryFactory
                .update(customer)
                .set(customer.birthDate, birthDate)
                .set(customer.phone, phone)
                .set(customer.address, address)
                .where(customer.id.eq(customerId))
                .execute();
    }
}
