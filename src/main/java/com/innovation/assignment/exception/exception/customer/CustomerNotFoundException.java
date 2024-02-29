package com.innovation.assignment.exception.exception.customer;

import com.innovation.assignment.exception.ApplicationException;
import com.innovation.assignment.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends ApplicationException {

    public CustomerNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.CUSTOMER_NOT_FOUND, ErrorCode.CUSTOMER_NOT_FOUND.getMessage());
    }
}
