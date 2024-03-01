package com.innovation.assignment.customer.presentation.dto.request;

public record DeleteCustomerRequestDto(
        Long customerId,
        String withdrawalReason
) {
}
