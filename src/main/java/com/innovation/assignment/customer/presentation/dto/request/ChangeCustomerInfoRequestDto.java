package com.innovation.assignment.customer.presentation.dto.request;

public record ChangeCustomerInfoRequestDto(
        String birthDate,
        String phone,
        String address,
        String addressDetail
) {
}
