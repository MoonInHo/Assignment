package com.innovation.assignment.customer.presentation.dto.request;

public record ChangePasswordRequestDto(
        String newPassword,
        String confirmNewPassword
) {
}
