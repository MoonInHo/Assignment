package com.innovation.assignment.customer.domain.repository;

import com.innovation.assignment.customer.domain.entity.Customer;
import com.innovation.assignment.customer.infrastructure.repository.CustomerQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerQueryRepository {
}
