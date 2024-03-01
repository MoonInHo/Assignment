package com.innovation.assignment.customer.domain.entity;

import com.innovation.assignment.customer.domain.vo.WithdrawalReason;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long customerId;

    @Embedded
    private WithdrawalReason withdrawalReason;

    private CustomerHistory(Long customerId, WithdrawalReason withdrawalReason) {
        this.customerId = customerId;
        this.withdrawalReason = withdrawalReason;
    }

    public static CustomerHistory createCustomerHistory(Long customerId, WithdrawalReason withdrawalReason) {
        return new CustomerHistory(customerId, withdrawalReason);
    }
}
