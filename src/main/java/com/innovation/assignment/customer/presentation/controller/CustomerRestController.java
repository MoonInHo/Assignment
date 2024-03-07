package com.innovation.assignment.customer.presentation.controller;

import com.innovation.assignment.customer.application.dto.request.CreateCustomerRequestDto;
import com.innovation.assignment.customer.application.service.CustomerService;
import com.innovation.assignment.customer.infrastructure.dto.response.GetCustomerResponseDto;
import com.innovation.assignment.customer.presentation.dto.request.ChangeCustomerInfoRequestDto;
import com.innovation.assignment.customer.presentation.dto.request.ChangePasswordRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerRestController {

    private final CustomerService customerService;

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

    @GetMapping("/{customerId}")
    public ResponseEntity<GetCustomerResponseDto> getCustomer(
            @PathVariable(name = "customerId") Long customerId
    ) {
        return ResponseEntity.ok().body(
                customerService.getCustomer(customerId)
        );
    }

    @PatchMapping("/{customerId}/password")
    public ResponseEntity<Void> changePassword(
            @PathVariable(name = "customerId") Long customerId,
            @RequestBody ChangePasswordRequestDto changePasswordRequestDto
    ) {
        customerService.changePassword(customerId, changePasswordRequestDto);
         return ResponseEntity.ok().build();
    }

    @PatchMapping("/{customerId}/info")
    public ResponseEntity<Void> modifyDetails(
            @PathVariable(name = "customerId") Long customerId,
            @RequestBody ChangeCustomerInfoRequestDto changeCustomerInfoRequestDto
    ) {
        customerService.modifyDetails(customerId, changeCustomerInfoRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable(name = "customerId") Long customerId
    ) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }
}
