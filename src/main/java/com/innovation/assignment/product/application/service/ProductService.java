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
import com.innovation.assignment.product.presentation.dto.request.ModifyProductRequestDto;
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
    public Page<GetProductResponseDto> getProducts(String category, Pageable pageable) {

        Page<GetProductResponseDto> products = productRepository.getProducts(getCategory(category), pageable);
        if (products.isEmpty()) {
            throw new EmptyProductListException();
        }
        return products;
    }

    @Transactional(readOnly = true)
    public GetProductResponseDto getProduct(Long productId) {
        return productRepository.getProductInfo(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Transactional
    public void modifyProduct(Long productId, ModifyProductRequestDto modifyProductRequestDto) {

        Product product = productRepository.getProduct(productId)
                .orElseThrow(ProductNotFoundException::new);

        product.modifyProductInfo( //TODO update 쿼리 직접 사용하는 방법 고민
                ProductName.of(modifyProductRequestDto.productName()),
                Category.checkCategory(modifyProductRequestDto.category()),
                Quantity.of(modifyProductRequestDto.quantity()),
                Price.of(modifyProductRequestDto.price())
        );
    }

    private void checkDuplicateProduct(RegisterProductRequestDto registerProductRequestDto) {
        boolean productNameExist = productRepository.isProductExist(ProductName.of(registerProductRequestDto.getProductName()));
        if (productNameExist) {
            throw new DuplicateProductException();
        }
    }

    private Category getCategory(String category) {
        if (category == null) {
            return null;
        }
        return Category.checkCategory(category);
    }
}
