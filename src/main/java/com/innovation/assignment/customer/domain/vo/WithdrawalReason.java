package com.innovation.assignment.customer.domain.vo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class WithdrawalReason {

    private final String WithdrawalReason;

    private WithdrawalReason(String withdrawalReason) {
        WithdrawalReason = withdrawalReason;
    }

    public static WithdrawalReason of(String withdrawalReason) {
        return new WithdrawalReason(withdrawalReason);
    }
}
