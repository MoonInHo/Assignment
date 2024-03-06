package com.innovation.assignment.product.application.service;

import com.innovation.assignment.exception.exceptions.product.DuplicateProductException;
import com.innovation.assignment.exception.exceptions.product.EmptyProductListException;
import com.innovation.assignment.product.application.dto.RegisterProductRequestDto;
import com.innovation.assignment.product.domain.enums.Category;
import com.innovation.assignment.product.domain.repository.ProductRepository;
import com.innovation.assignment.product.infrastructure.dto.response.GetProductResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

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

    private Pageable pageable;

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

    @Test
    @DisplayName("상품 목록 조회 - 비어있는 상품 목록 조회시 예외 발생")
    void emptyProductList_getProducts_throwException() {
        //given
        List<GetProductResponseDto> emptyProductList = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 10);
        PageImpl<GetProductResponseDto> emptyPage = new PageImpl<>(emptyProductList, pageRequest, 0);

        given(productRepository.getProductsInfo(any())).willReturn(emptyPage);

        //when
        Throwable throwable = catchThrowable(() -> productService.getProducts(pageable));

        //then
        assertThat(throwable).isInstanceOf(EmptyProductListException.class);
        assertThat(throwable).hasMessage("상품 목록이 비어있습니다.");
    }

    @Test
    @DisplayName("상품 목록 조회 - 비어있지 않은 상품 목록 조회시 상품 목록 반환")
    void notEmptyProductList_getProducts_returnProducts() {
        //given
        GetProductResponseDto getProductResponseDto = new GetProductResponseDto(
                1L,
                "테스트 상품명",
                Category.checkCategory("카테고리1"),
                10,
                15000,
                "테스트 상품 코드"
        );

        List<GetProductResponseDto> productList = new ArrayList<>();
        productList.add(getProductResponseDto);
        PageRequest pageRequest = PageRequest.of(0, 10);
        PageImpl<GetProductResponseDto> productPage = new PageImpl<>(productList, pageRequest, 2);

        given(productRepository.getProductsInfo(any())).willReturn(productPage);

        //when
        Page<GetProductResponseDto> products = productService.getProducts(pageable);

        //then
        assertThat(products).isNotEmpty();
        assertThat(products).hasSize(1);
    }

    @Test
    @DisplayName("상품 카테고리 조회 - 비어있는 상품 카테고리 조회시 예외 발생")
    void emptyProductCategory_getProductsByCategory_throwException() {
        //given
        String category = "카테고리1";

        List<GetProductResponseDto> emptyProductList = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 10);
        PageImpl<GetProductResponseDto> emptyPage = new PageImpl<>(emptyProductList, pageRequest, 0);

        given(productRepository.getProductsByCategory(any(), any())).willReturn(emptyPage);

        //when
        Throwable throwable = catchThrowable(() -> productService.getProductsByCategory(category, pageable));

        //then
        assertThat(throwable).isInstanceOf(EmptyProductListException.class);
        assertThat(throwable).hasMessage("상품 목록이 비어있습니다.");
    }

    @Test
    @DisplayName("상품 카테고리 조회 - 비어있지 않은 상품 카테고리 조회시 상품 목록 반환")
    void notEmptyProductCategory_getProductsByCategory_returnProducts() {
        //given
        String category = "카테고리1";
        GetProductResponseDto getProductResponseDto = new GetProductResponseDto(
                1L,
                "테스트 상품명",
                Category.checkCategory("카테고리1"),
                10,
                15000,
                "테스트 상품 코드"
        );

        List<GetProductResponseDto> productList = new ArrayList<>();
        productList.add(getProductResponseDto);
        PageRequest pageRequest = PageRequest.of(0, 10);
        PageImpl<GetProductResponseDto> productPage = new PageImpl<>(productList, pageRequest, 2);

        given(productRepository.getProductsByCategory(any(), any())).willReturn(productPage);

        //when
        Page<GetProductResponseDto> products = productService.getProductsByCategory(category, pageable);

        //then
        assertThat(products).isNotEmpty();
        assertThat(products).hasSize(1);
    }
}