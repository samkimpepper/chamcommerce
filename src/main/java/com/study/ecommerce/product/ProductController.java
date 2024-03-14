package com.study.ecommerce.product;

import com.study.ecommerce.product.dto.ProductCreateRequest;
import com.study.ecommerce.product.dto.ProductResponse;
import com.study.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/info")
    public ResponseEntity<ProductResponse> createProductInfo(@RequestBody ProductCreateRequest request) {
        return ResponseEntity.ok().body(productService.createProductInfo(request));
    }
}
