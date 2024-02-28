package com.innovation.assignment.exception.exception.member;

import com.innovation.assignment.exception.ApplicationException;
import com.innovation.assignment.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends ApplicationException {

    public MemberNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.MEMBER_NOT_FOUND, ErrorCode.MEMBER_NOT_FOUND.getMessage());
    }
}
