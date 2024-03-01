package com.innovation.assignment.customer.presentation.dto.request;

public record ChangePasswordRequestDto(
        Long customerId,
        String newPassword,
        String confirmNewPassword
) {
}
