package com.innovation.assignment.member.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

@DisplayName("[유닛 테스트] - 비밀번호 도메인")
class PasswordTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("비밀번호 입력 - 비밀번호 미입력시 예외 발생")
    void nullAndEmptyPassword_throwException(String password) {
        //given & when
        Throwable throwable = catchThrowable(() -> Password.of(password));

        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
        assertThat(throwable).hasMessage("비밀번호를 입력하세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {" testPassword123", "testPassword 123", "test Password123", "testPassword123 "})
    @DisplayName("비밀번호 입력 - 비밀번호에 공백 포함시 예외 발생")
    void passwordContainsWhitespace_throwException(String password) {
        //given & when
        Throwable throwable = catchThrowable(() -> Password.of(password));

        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
        assertThat(throwable).hasMessage("비밀번호엔 공백을 포함할 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"testPassword123!", "proper!Password123", "GoodPassword12#"})
    @DisplayName("비밀번호 입력 - 올바른 형식의 비밀번호 입력시 비밀번호 객체 반환")
    void properFormatPassword_returnPasswordObject(String password) {
        //given & when
        Password passwordObject = Password.of(password);

        //then
        assertThat(passwordObject).isInstanceOf(Password.class);
    }
}