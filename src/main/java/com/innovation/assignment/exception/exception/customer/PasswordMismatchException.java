package com.innovation.assignment.exception.exception.customer;

import com.innovation.assignment.exception.ApplicationException;
import com.innovation.assignment.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends ApplicationException {

    public PasswordMismatchException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.PASSWORD_MISMATCH, ErrorCode.PASSWORD_MISMATCH.getMessage());
    }
}
