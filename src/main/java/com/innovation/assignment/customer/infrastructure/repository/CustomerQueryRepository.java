package com.innovation.assignment.customer.infrastructure.repository;

import com.innovation.assignment.customer.domain.entity.Customer;
import com.innovation.assignment.customer.domain.vo.Email;
import com.innovation.assignment.customer.domain.vo.Phone;
import com.innovation.assignment.customer.infrastructure.dto.response.GetCustomerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerQueryRepository {

    boolean isEmailExist(Email email);

    boolean isPhoneExist(Phone phone);

    Page<GetCustomerResponseDto> getCustomers(Pageable pageable);

    Optional<GetCustomerResponseDto> getCustomerInfo(Long customerId);

    Optional<Customer> getCustomer(Long customerId);
}
