package com.innovation.assignment.exception.exception.customer;

import com.innovation.assignment.exception.ApplicationException;
import com.innovation.assignment.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class SamePasswordException extends ApplicationException {

    public SamePasswordException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.SAME_PASSWORD, ErrorCode.SAME_PASSWORD.getMessage());
    }
}
