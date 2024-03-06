package com.innovation.assignment.product.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

@DisplayName("[유닛 테스트] - 상품명 도메인")
class ProductNameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("상품명 입력 - 상품명 미입력시 예외 발생")
    void nullAndEmptyProductName_throwException(String productName) {
        //given & when
        Throwable throwable = catchThrowable(() -> ProductName.of(productName));

        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
        assertThat(throwable).hasMessage("상품명을 입력하세요.");
    }

    @Test
    @DisplayName("상품명 입력 - 올바른 형식의 상품명 입력시 상품명 객체 반환")
    void properProductNameFormat_returnProductNameObject() {
        //given & when
        ProductName productName = ProductName.of("올바른 상품명");

        //then
        assertThat(productName).isInstanceOf(ProductName.class);
    }
}