package com.innovation.assignment.customer.application.event;

import com.innovation.assignment.customer.presentation.dto.request.DeleteCustomerRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerHasDeletedEvent {

    private DeleteCustomerRequestDto deleteCustomerRequestDto;
}
