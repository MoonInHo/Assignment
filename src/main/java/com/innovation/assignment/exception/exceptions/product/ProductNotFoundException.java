package com.innovation.assignment.exception.exceptions.product;

import com.innovation.assignment.exception.ApplicationException;
import com.innovation.assignment.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends ApplicationException {

    public ProductNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.PRODUCT_NOT_FOUND, ErrorCode.PRODUCT_NOT_FOUND.getMessage());
    }
}
