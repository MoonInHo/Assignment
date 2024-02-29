package com.innovation.assignment.exception.exception.customer;

import com.innovation.assignment.exception.ApplicationException;
import com.innovation.assignment.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class PasswordConfirmationMismatchException extends ApplicationException {

    public PasswordConfirmationMismatchException() {
        super(
                HttpStatus.UNAUTHORIZED,
                ErrorCode.PASSWORD_CONFIRMATION_MISMATCH,
                ErrorCode.PASSWORD_CONFIRMATION_MISMATCH.getMessage()
        );
    }
}
