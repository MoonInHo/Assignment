package com.innovation.assignment.product.application.service;

import com.innovation.assignment.exception.exceptions.product.DuplicateProductException;
import com.innovation.assignment.exception.exceptions.product.EmptyProductListException;
import com.innovation.assignment.product.application.dto.RegisterProductRequestDto;
import com.innovation.assignment.product.domain.enums.Category;
import com.innovation.assignment.product.domain.repository.ProductRepository;
import com.innovation.assignment.product.domain.vo.ProductName;
import com.innovation.assignment.product.infrastructure.dto.response.GetProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void registerProduct(RegisterProductRequestDto registerProductRequestDto) {

        checkDuplicateProduct(registerProductRequestDto);

        productRepository.save(registerProductRequestDto.toEntity());
    }

    @Transactional(readOnly = true)
    public Page<GetProductResponseDto> getProducts(Pageable pageable) {

        Page<GetProductResponseDto> products = productRepository.getProductsInfo(pageable);
        if (products.isEmpty()) {
            throw new EmptyProductListException();
        }
        return products;
    }

    @Transactional(readOnly = true)
    public Page<GetProductResponseDto> getProductsByCategory(String category, Pageable pageable) {

        Page<GetProductResponseDto> products =
                productRepository.getProductsByCategory(Category.checkCategory(category), pageable);
        if (products.isEmpty()) {
            throw new EmptyProductListException();
        }
        return products;
    }

    private void checkDuplicateProduct(RegisterProductRequestDto registerProductRequestDto) {
        boolean productNameExist = productRepository.isProductExist(ProductName.of(registerProductRequestDto.getProductName()));
        if (productNameExist) {
            throw new DuplicateProductException();
        }
    }
}
