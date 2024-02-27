package com.innovation.assignment.member.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

@DisplayName("[유닛 테스트] - 이메일 도메인")
class EmailTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이메일 입력 - 이메일 미입력시 예외 발생")
    void nullAndEmptyEmail_throwException(String email) {
        //given & when
        Throwable throwable = catchThrowable(() -> Email.of(email));

        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
        assertThat(throwable).hasMessage("이메일을 입력하세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"test123", "test123@", "test123@123.123", "test123@naver.123"})
    @DisplayName("이메일 입력 - 올바르지 않은 형식의 이메일 입력시 예외 발생")
    void invalidEmailFormat_throwException(String email) {
        //given & when
        Throwable throwable = catchThrowable(() -> Email.of(email));

        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
        assertThat(throwable).hasMessage("이메일 형식이 올바르지 않습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"proper123@naver.com", "user123@gmail.com", "test123@daum.net"})
    @DisplayName("이메일 입력 - 올바른 형식의 이메일 입력시 이메일 객체 반환")
    void properFormatEmail_returnEmailObject(String email) {
        //given & when
        Email emailObject = Email.of(email);

        //then
        assertThat(emailObject).isInstanceOf(Email.class);
    }
}