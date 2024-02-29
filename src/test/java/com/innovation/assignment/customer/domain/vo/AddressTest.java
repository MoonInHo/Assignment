package com.innovation.assignment.customer.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[유닛 테스트] - 주소 도메인")
class AddressTest {

    @Test
    @DisplayName("주소 입력 - 올바른 형식의 주소 입력시 주소 객체 반환")
    void properFormatAddress_returnAddressObject() {
        //given & when
        Address address = Address.of("서울특별시 강남구 강남대로", "지하396");

        //then
        assertThat(address).isInstanceOf(Address.class);
    }
}