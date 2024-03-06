package com.innovation.assignment.product.presentation.controller;

import com.innovation.assignment.product.application.dto.RegisterProductRequestDto;
import com.innovation.assignment.product.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
