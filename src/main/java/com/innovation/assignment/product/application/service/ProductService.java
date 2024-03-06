package com.innovation.assignment.product.application.service;

import com.innovation.assignment.exception.exceptions.product.DuplicateProductException;
import com.innovation.assignment.product.application.dto.RegisterProductRequestDto;
import com.innovation.assignment.product.domain.repository.ProductRepository;
import com.innovation.assignment.product.domain.vo.ProductName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void registerProduct(RegisterProductRequestDto registerProductRequestDto) {

        checkDuplicateProduct(registerProductRequestDto);

        productRepository.save(registerProductRequestDto.toEntity());
    }

    private void checkDuplicateProduct(RegisterProductRequestDto registerProductRequestDto) {
        boolean productNameExist = productRepository.isProductExist(ProductName.of(registerProductRequestDto.getProductName()));
        if (productNameExist) {
            throw new DuplicateProductException();
        }
    }
}
