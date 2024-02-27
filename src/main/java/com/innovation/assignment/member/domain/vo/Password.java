package com.innovation.assignment.member.domain.vo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Password {

    private final String password;

    private Password(String password) {
        this.password = password;
    }

    public static Password of(String password) {

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호를 입력하세요.");
        }
        if (password.contains(" ")) {
            throw new IllegalArgumentException("비밀번호엔 공백을 포함할 수 없습니다.");
        }
        return new Password(password);
    }
}
