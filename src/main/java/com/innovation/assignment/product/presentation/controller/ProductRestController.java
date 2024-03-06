package com.innovation.assignment.product.presentation.controller;

import com.innovation.assignment.product.application.dto.RegisterProductRequestDto;
import com.innovation.assignment.product.application.service.ProductService;
import com.innovation.assignment.product.infrastructure.dto.response.GetProductResponseDto;
import com.innovation.assignment.product.presentation.dto.request.ModifyProductRequestDto;
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
            @RequestParam(name = "category", required = false) String category,
            Pageable pageable
    ) {
        return ResponseEntity.ok().body(productService.getProducts(category ,pageable));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<GetProductResponseDto> getProduct(
            @PathVariable(name = "productId") Long productId
    ) {
        return ResponseEntity.ok().body(productService.getProduct(productId));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Void> ModifyProduct(
            @PathVariable(name = "productId") Long productId,
            @RequestBody ModifyProductRequestDto modifyProductRequestDto
    ) {
        productService.modifyProduct(productId, modifyProductRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable(name = "productId") Long productId
    ) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }
}
