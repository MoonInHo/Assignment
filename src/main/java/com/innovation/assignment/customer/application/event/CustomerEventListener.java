package com.innovation.assignment.customer.application.event;

import com.innovation.assignment.customer.application.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerEventListener {

    private final CustomerService customerService;

    @Async
    @EventListener
    public void onCustomerHasDeletedEvent(CustomerHasDeletedEvent customerHasDeletedEvent) {
        customerService.addCustomerHistory(customerHasDeletedEvent.getDeleteCustomerRequestDto());
    }
}
