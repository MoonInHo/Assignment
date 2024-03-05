package com.innovation.assignment.exception.exceptions.customer;

import com.innovation.assignment.exception.ApplicationException;
import com.innovation.assignment.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends ApplicationException {

    public DuplicateEmailException() {
        super(HttpStatus.CONFLICT, ErrorCode.DUPLICATE_EMAIL, ErrorCode.DUPLICATE_EMAIL.getMessage());
    }
}
