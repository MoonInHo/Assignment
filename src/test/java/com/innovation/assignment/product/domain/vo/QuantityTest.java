package com.innovation.assignment.product.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

@DisplayName("[유닛 테스트] - 수량 도메인")
class QuantityTest {

    @ParameterizedTest
    @NullSource
    @DisplayName("수량 입력 - 수량 미입력시 예외 발생")
    void nullQuantity_throwException(Integer quantity) {
        //given & when
        Throwable throwable = catchThrowable(() -> Quantity.of(quantity));

        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
        assertThat(throwable).hasMessage("수량을 입력하세요.");
    }

    @Test
    @DisplayName("수량 입력 - 올바른 형식의 수량 입력시 수량 객체 반환")
    void properQuantityFormat_returnQuantityObject() {
        //given & when
        Quantity quantity = Quantity.of(10);

        //then
        assertThat(quantity).isInstanceOf(Quantity.class);
    }
}