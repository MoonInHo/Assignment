package com.innovation.assignment.customer.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

@DisplayName("[유닛 테스트] - 연락처 도메인")
class PhoneTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("연락처 입력 - 연락처 미입력시 예외 발생")
    void nullAndEmptyPhone_throwException(String phone) {
        //given & when
        Throwable throwable = catchThrowable(() -> Phone.of(phone));

        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
        assertThat(throwable).hasMessage("연락처를 입력하세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"01012345678", "010.1234.5678", " 010-1234-5678", "02-1234-5678"})
    @DisplayName("연락처 입력 - 올바르지 않은 형식의 연락처 입력시 예외 발생")
    void invalidPhoneFormat_throwException(String phone) {
        //given & when
        Throwable throwable = catchThrowable(() -> Phone.of(phone));

        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
        assertThat(throwable).hasMessage("연락처 형식이 올바르지 않습니다.");
    }

    @Test
    @DisplayName("연락처 입력 - 올바른 형식의 연락처 입력시 연락처 객체 반환")
    void properFormatPhone_returnPhoneObject() {
        //given & when
        Phone phone = Phone.of("010-1234-5678");

        //then
        assertThat(phone).isInstanceOf(Phone.class);
    }
}