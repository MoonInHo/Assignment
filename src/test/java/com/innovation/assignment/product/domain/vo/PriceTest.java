package com.innovation.assignment.product.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

@DisplayName("[유닛 테스트] - 단가 도메인")
class PriceTest {

    @ParameterizedTest
    @NullSource
    @DisplayName("단가 입력 - 단가 미입력시 예외 발생")
    void nullAndEmptyPrice_throwException(Integer price) {
        //given & when
        Throwable throwable = catchThrowable(() -> Price.of(price));

        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
        assertThat(throwable).hasMessage("단가를 입력하세요.");
    }

    @Test
    @DisplayName("단가 입력 - 올바른 형식의 단가 입력시 단가 객체 반환")
    void properPriceFormat_returnPriceObject() {
        //given & when
        Integer priceExample = 15000;
        Price price = Price.of(priceExample);

        //then
        assertThat(price).isInstanceOf(Price.class);
    }
}