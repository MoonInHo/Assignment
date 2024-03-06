package com.innovation.assignment.exception.exceptions.product;

import com.innovation.assignment.exception.ApplicationException;
import com.innovation.assignment.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class DuplicateProductException extends ApplicationException {

    public DuplicateProductException() {
        super(HttpStatus.CONFLICT, ErrorCode.DUPLICATE_PRODUCT, ErrorCode.DUPLICATE_PRODUCT.getMessage());
    }
}
