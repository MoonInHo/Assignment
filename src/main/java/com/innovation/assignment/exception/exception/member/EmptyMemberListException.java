package com.innovation.assignment.exception.exception.member;

import com.innovation.assignment.exception.ApplicationException;
import com.innovation.assignment.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class EmptyMemberListException extends ApplicationException {

    public EmptyMemberListException() {
        super(HttpStatus.NO_CONTENT, ErrorCode.EMPTY_MEMBER_LIST, ErrorCode.EMPTY_MEMBER_LIST.getMessage());
    }
}
