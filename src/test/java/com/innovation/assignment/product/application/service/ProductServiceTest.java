package com.innovation.assignment.product.application.service;

import com.innovation.assignment.exception.exceptions.product.DuplicateProductException;
import com.innovation.assignment.exception.exceptions.product.EmptyProductListException;
import com.innovation.assignment.exception.exceptions.product.ProductNotFoundException;
import com.innovation.assignment.product.application.dto.RegisterProductRequestDto;
import com.innovation.assignment.product.domain.entity.Product;
import com.innovation.assignment.product.domain.enums.Category;
import com.innovation.assignment.product.domain.repository.ProductRepository;
import com.innovation.assignment.product.domain.vo.Price;
import com.innovation.assignment.product.domain.vo.ProductName;
import com.innovation.assignment.product.domain.vo.Quantity;
import com.innovation.assignment.product.infrastructure.dto.response.GetProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("[유닛 테스트] - 상품 서비스")
class ProductServiceTest {

    private Pageable pageable;

    private RegisterProductRequestDto registerProductRequestDto;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setProductInfo() {
        registerProductRequestDto = new RegisterProductRequestDto(
                "중복된 상품명",
                "카테고리1",
                200,
                15000
        );
    }

    @Test
    @DisplayName("상품 등록 - 이미 존재하는 상품 등록시 예외 발생")
    void existProduct_registerProduct_throwException() {
        //given
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
        String category = "카테고리1";
        List<GetProductResponseDto> emptyProductList = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 10);
        PageImpl<GetProductResponseDto> emptyPage = new PageImpl<>(emptyProductList, pageRequest, 0);

        given(productRepository.getProducts(any(), any())).willReturn(emptyPage);

        //when
        Throwable throwable = catchThrowable(() -> productService.getProducts(category, pageable));

        //then
        assertThat(throwable).isInstanceOf(EmptyProductListException.class);
        assertThat(throwable).hasMessage("상품 목록이 비어있습니다.");
    }

    @Test
    @DisplayName("상품 목록 조회 - 비어있지 않은 상품 목록 조회시 상품 목록 반환")
    void notEmptyProductList_getProducts_returnProducts() {
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

        given(productRepository.getProducts(any(), any())).willReturn(productPage);

        //when
        Page<GetProductResponseDto> products = productService.getProducts(category, pageable);

        //then
        assertThat(products).isNotEmpty();
        assertThat(products).hasSize(1);
    }

    @Test
    @DisplayName("상품 조회 - 존재하지 않는 상품 조회시 예외 발생")
    void nonExistProduct_getProduct_throwException() {
        //given
        given(productRepository.getProductInfo(any())).willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> productService.getProduct(1L));

        //then
        assertThat(throwable).isInstanceOf(ProductNotFoundException.class);
        assertThat(throwable).hasMessage("상품을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("상품 조회 - 존재하는 상품 조회시 상품 정보 반환")
    void existProduct_getProduct_returnProductInfo() {
        //given
        GetProductResponseDto getProductResponseDto = new GetProductResponseDto(
                1L,
                "테스트 상품명",
                Category.checkCategory("카테고리1"),
                10,
                15000,
                "테스트 상품 코드"
        );
        given(productRepository.getProductInfo(any())).willReturn(Optional.of(getProductResponseDto));

        //when
        GetProductResponseDto product = productService.getProduct(1L);

        //then
        assertThat(product.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("상품 삭제 - 존재하는 상품 번호로 상품 삭제시 상품 삭제")
    void existProductId_deleteProduct_deleteProduct() {
        //given
        Product product = registerProductRequestDto.toEntity();
        given(productRepository.getProduct(1L)).willReturn(Optional.ofNullable(product));

        //when
        productService.deleteProduct(1L);

        //then
        verify(productRepository, times(1)).delete(product);
    }
}