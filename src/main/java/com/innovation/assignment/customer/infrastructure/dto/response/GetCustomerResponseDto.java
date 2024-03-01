package com.innovation.assignment.customer.infrastructure.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetCustomerResponseDto {

    private Long id;
    private String email;
    private String password;
    private String birthDate;
    private String phone;
    private String address;
    private String addressDetail;
}
