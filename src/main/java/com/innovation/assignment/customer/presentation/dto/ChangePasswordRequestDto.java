package com.innovation.assignment.customer.presentation.dto;

public record ChangePasswordRequestDto(
        Long customerId,
        String newPassword,
        String confirmNewPassword
) {
}
