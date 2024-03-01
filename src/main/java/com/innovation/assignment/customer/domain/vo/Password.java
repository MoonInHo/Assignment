package com.innovation.assignment.customer.domain.vo;

import com.innovation.assignment.exception.exception.customer.SamePasswordException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public Password encodedPassword(PasswordEncoder passwordEncoder) {
        return new Password(passwordEncoder.encode(password));
    }

    public Password changePassword(Password newPassword, PasswordEncoder passwordEncoder) {
        
        if (isMatchesCurrentPassword(newPassword, passwordEncoder)) {
            throw new SamePasswordException();
        }
        return newPassword.encodedPassword(passwordEncoder);
    }

    private boolean isMatchesCurrentPassword(Password newPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(newPassword.password, this.password);
    }
}
