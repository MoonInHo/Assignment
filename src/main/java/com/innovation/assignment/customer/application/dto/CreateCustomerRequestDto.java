package com.innovation.assignment.customer.application.dto;

import com.innovation.assignment.customer.domain.entity.Customer;
import com.innovation.assignment.customer.domain.vo.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequestDto {

    private String email;
    private String password;
    private String birthDate;
    private String phone;
    private String address;
    private String addressDetail;

    public Customer toEntity() {
        return Customer.createCustomer(
                Email.of(email),
                Password.of(password),
                BirthDate.of(birthDate),
                Phone.of(phone),
                Address.of(address, addressDetail)
        );
    }
}
