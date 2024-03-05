package com.innovation.assignment.exception.exceptions.customer;

import com.innovation.assignment.exception.ApplicationException;
import com.innovation.assignment.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class EmptyCustomerListException extends ApplicationException {

    public EmptyCustomerListException() {
        super(HttpStatus.NO_CONTENT, ErrorCode.EMPTY_CUSTOMER_LIST, ErrorCode.EMPTY_CUSTOMER_LIST.getMessage());
    }
}
