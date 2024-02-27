package com.innovation.assignment.exception.exception;

import com.innovation.assignment.exception.ApplicationException;
import com.innovation.assignment.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class DuplicatePhoneException extends ApplicationException {

    public DuplicatePhoneException() {
        super(HttpStatus.CONFLICT, ErrorCode.DUPLICATE_PHONE, ErrorCode.DUPLICATE_PHONE.getMessage());
    }
}
