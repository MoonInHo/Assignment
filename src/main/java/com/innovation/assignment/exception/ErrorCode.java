package com.innovation.assignment.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Global
    INVALID_REQUEST("잘못된 요청"),

    // Customer
    DUPLICATE_EMAIL("이미 사용중인 이메일 입니다."),
    DUPLICATE_PHONE("해당 연락처로 가입 정보가 존재합니다."),
    EMPTY_CUSTOMER_LIST("고객 목록이 비어있습니다."),
    CUSTOMER_NOT_FOUND("고객을 찾을 수 없습니다."),
    PASSWORD_MISMATCH("비밀번호가 일치하지 않습니다."),
    PASSWORD_CONFIRMATION_MISMATCH("변경할 비밀번호와 비밀번호 확인이 일치하지 않습니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}