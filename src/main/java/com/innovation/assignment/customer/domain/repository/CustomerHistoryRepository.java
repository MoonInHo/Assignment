package com.innovation.assignment.customer.domain.repository;

import com.innovation.assignment.customer.domain.entity.CustomerHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerHistoryRepository extends JpaRepository<CustomerHistory, Long> {
}
