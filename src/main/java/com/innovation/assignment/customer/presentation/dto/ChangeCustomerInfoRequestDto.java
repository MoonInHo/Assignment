package com.innovation.assignment.customer.presentation.dto;

public record ChangeCustomerInfoRequestDto(
        Long customerId,
        String birthDate,
        String phone,
        String address,
        String addressDetail
) {
}
