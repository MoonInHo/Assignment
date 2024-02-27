package com.innovation.assignment.member.domain.vo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class BirthDate {

    private final String birthDate;

    private BirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public static BirthDate of(String birthDate) {

        //생년월일 형식 검증
        //생년월일이 미래일 경우 예외 발생
        return new BirthDate(birthDate);
    }
}
