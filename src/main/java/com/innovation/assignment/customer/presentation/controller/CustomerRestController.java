package com.innovation.assignment.customer.presentation.controller;

import com.innovation.assignment.customer.application.dto.CreateCustomerRequestDto;
import com.innovation.assignment.customer.application.service.CustomerService;
import com.innovation.assignment.customer.infrastructure.dto.GetCustomerResponseDto;
import com.innovation.assignment.customer.presentation.dto.ChangePasswordRequestDto;
import com.innovation.assignment.customer.presentation.dto.SearchCustomerByEmailRequestDto;
import com.innovation.assignment.customer.presentation.dto.SearchCustomerByPhoneRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerRestController {

    private final CustomerService customerService; // 규모 확장시 각 역할별로 service 클래스 분할

    @PostMapping
    public ResponseEntity<Void> createCustomer(
            @RequestBody CreateCustomerRequestDto createCustomerRequestDto
    ) {
        customerService.signUp(createCustomerRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<GetCustomerResponseDto>> getCustomers(Pageable pageable) {
        return ResponseEntity.ok().body(customerService.getCustomers(pageable));
    }

    @PostMapping("/search/email")
    public ResponseEntity<GetCustomerResponseDto> searchCustomerByEmail(
            @RequestBody SearchCustomerByEmailRequestDto searchCustomerByEmailRequestDto
    ) {
        return ResponseEntity.ok().body(
                customerService.searchCustomerByEmail(searchCustomerByEmailRequestDto)
        );
    }

    @PostMapping("/search/phone")
    public ResponseEntity<GetCustomerResponseDto> searchCustomerByPhone(
            @RequestBody SearchCustomerByPhoneRequestDto searchCustomerByPhoneRequestDto
    ) {
        return ResponseEntity.ok().body(
                customerService.searchCustomerByPhone(searchCustomerByPhoneRequestDto)
        );
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(
            @RequestBody ChangePasswordRequestDto changePasswordRequestDto
    ) {
        customerService.changePassword(changePasswordRequestDto);
        return ResponseEntity.ok().build();
    }
}
