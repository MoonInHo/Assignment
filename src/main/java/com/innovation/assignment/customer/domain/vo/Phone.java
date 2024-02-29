package com.innovation.assignment.customer.domain.vo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Phone {

    private final String phone;

    private Phone(String phone) {
        this.phone = phone;
    }

    public static Phone of(String phone) {

        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("연락처를 입력하세요.");
        }
        if (!phone.matches("^010-[0-9]{4}-[0-9]{4}$")) {
            throw new IllegalArgumentException("연락처 형식이 올바르지 않습니다.");
        }
        return new Phone(phone);
    }
}
