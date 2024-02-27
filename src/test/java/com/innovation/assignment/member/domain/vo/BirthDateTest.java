package com.innovation.assignment.member.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[유닛 테스트] - 생년월일 도메인")
class BirthDateTest {

    @ParameterizedTest
    @ValueSource(strings = {"1995.11.20", "2000.02.25", "2020.12.25"})
    @DisplayName("생년월일 입력 - 올바른 형식의 생년월일 입력시 생년월일 객체 반환")
    void properFormatBirthDate_returnBirthDateObject(String birthDate) {
        //given & when
        BirthDate emailObject = BirthDate.of(birthDate);

        //then
        assertThat(emailObject).isInstanceOf(BirthDate.class);
    }
}