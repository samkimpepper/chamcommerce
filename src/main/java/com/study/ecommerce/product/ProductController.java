package com.study.ecommerce.product;

import com.study.ecommerce.auth.token.MemberDetails;
import com.study.ecommerce.product.dto.ProductItemCreateRequest;
import com.study.ecommerce.product.dto.ProductCreateRequest;
import com.study.ecommerce.product.dto.ProductResponse;
import com.study.ecommerce.product.dto.RestockRequest;
import com.study.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/products/info")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductResponse> createProductInfo(
            @AuthenticationPrincipal MemberDetails member,
            @RequestBody ProductCreateRequest request) {
        return ResponseEntity.ok().body(productService.createProductInfo(request, member.getId()));
    }

    @PostMapping("/products/{productId}/option-groups")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductResponse> createProductItem(
            @PathVariable("productId") Long productId,
            @RequestBody ProductItemCreateRequest request) {
        return ResponseEntity.ok().body(productService.createProductItem(productId, request));
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponse> showProduct(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok().body(productService.getProduct(productId));
    }

    @PutMapping("/products/{productId}/restock")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductResponse> restockProduct(
            @PathVariable("productId") Long productId,
            @RequestBody List<RestockRequest> requests) {
        return ResponseEntity.ok().body(productService.restock(productId, requests));
    }

}
