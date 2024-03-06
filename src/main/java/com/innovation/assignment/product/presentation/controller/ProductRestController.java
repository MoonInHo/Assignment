package com.innovation.assignment.product.presentation.controller;

import com.innovation.assignment.product.application.dto.RegisterProductRequestDto;
import com.innovation.assignment.product.application.service.ProductService;
import com.innovation.assignment.product.infrastructure.dto.response.GetProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> registerProduct(
            @RequestBody RegisterProductRequestDto registerProductRequestDto
    ) {
        productService.registerProduct(registerProductRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<GetProductResponseDto>> getProducts(
            Pageable pageable
    ) {
        return ResponseEntity.ok().body(productService.getProducts(pageable));
    }

    @GetMapping("/{category}")
    public ResponseEntity<Page<GetProductResponseDto>> getProductsByCategory(
            @PathVariable("category") String category,
            Pageable pageable
    ) {
        return ResponseEntity.ok().body(productService.getProductsByCategory(category, pageable));
    }
}
