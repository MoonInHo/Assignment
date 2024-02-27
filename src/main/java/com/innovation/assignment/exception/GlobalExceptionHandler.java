package com.innovation.assignment.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponseDto> onApplicationException(ApplicationException exception) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                exception.getErrorCode(),
                exception.getMessage()
        );

        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(errorResponseDto);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MissingRequestHeaderException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorResponseDto> onIllegalRequestException(Exception exception) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                ErrorCode.INVALID_REQUEST,
                exception.getMessage()
        );

        return ResponseEntity
                .badRequest()
                .body(errorResponseDto);
    }
}
