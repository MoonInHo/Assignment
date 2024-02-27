package com.innovation.assignment.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Global
    INVALID_REQUEST("잘못된 요청"),

    // Member
    DUPLICATE_EMAIL("이미 사용중인 이메일 입니다."),
    DUPLICATE_PHONE("해당 연락처로 가입 정보가 존재합니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}