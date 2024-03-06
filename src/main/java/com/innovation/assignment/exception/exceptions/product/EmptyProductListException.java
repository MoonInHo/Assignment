package com.innovation.assignment.exception.exceptions.product;

import com.innovation.assignment.exception.ApplicationException;
import com.innovation.assignment.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class EmptyProductListException extends ApplicationException {

    public EmptyProductListException() {
        super(HttpStatus.NO_CONTENT, ErrorCode.EMPTY_PRODUCT_LIST, ErrorCode.EMPTY_PRODUCT_LIST.getMessage());
    }
}
