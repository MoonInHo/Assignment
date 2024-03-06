package com.innovation.assignment.product.application.service;

import com.innovation.assignment.exception.exceptions.product.DuplicateProductException;
import com.innovation.assignment.product.application.dto.RegisterProductRequestDto;
import com.innovation.assignment.product.domain.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("[유닛 테스트] - 상품 서비스")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("상품 등록 - 이미 존재하는 상품 등록시 예외 발생")
    void existProduct_registerProduct_throwException() {
        //given
        RegisterProductRequestDto registerProductRequestDto = new RegisterProductRequestDto(
                "중복된 상품명",
                "카테고리1",
                200,
                15000
        );

        given(productRepository.isProductExist(any())).willReturn(true);

        //when
        Throwable throwable = catchThrowable(() -> productService.registerProduct(registerProductRequestDto));

        //then
        assertThat(throwable).isInstanceOf(DuplicateProductException.class);
        assertThat(throwable).hasMessage("이미 존재하는 상품입니다.");
    }

    @Test
    @DisplayName("상품 등록 - 올바른 정보로 상품 등록시 상품 저장")
    void properProductInfo_registerProduct_saveProductInfo() {
        //given
        RegisterProductRequestDto registerProductRequestDto = new RegisterProductRequestDto(
                "올바른 상품명",
                "카테고리1",
                200,
                15000
        );

        given(productRepository.isProductExist(any())).willReturn(false);

        //when
        productService.registerProduct(registerProductRequestDto);

        //then
        verify(productRepository, times(1)).save(any());
    }
}