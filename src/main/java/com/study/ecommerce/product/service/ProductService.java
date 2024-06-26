package com.study.ecommerce.product.service;

import com.study.ecommerce.product.domain.*;
import com.study.ecommerce.product.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductOptionDetailRepository productOptionDetailRepository;
    private final ProductItemRepository productItemRepository;

    @Transactional
    public ProductResponse createProductInfo(ProductCreateRequest request, Long sellerId) {
        Product product = request.toEntity(sellerId);
        productRepository.save(product);

        for (OptionCreateRequest optionCreateRequest : request.getOptions()) {
            ProductOption productOption = optionCreateRequest.toEntity(product);
            productOptionRepository.save(productOption);

            for (DetailCreateRequest detailCreateRequest : optionCreateRequest.getDetails()) {
                ProductOptionDetail productOptionDetail = detailCreateRequest.toEntity(productOption);
                productOptionDetailRepository.save(productOptionDetail);

                productOption.addDetail(productOptionDetail);
            }
            productOptionRepository.save(productOption);
        }

        return generateProductResponse(product);
    }

    @Transactional
    public ProductResponse createProductItem(Long productId, ProductItemCreateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        List<ProductOptionDetail> details = new ArrayList<>();
        for (Long detailId : request.getDetailIds()) {
            ProductOptionDetail productOptionDetail = productOptionDetailRepository.findById(detailId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션입니다."));
            details.add(productOptionDetail);
        }

        ProductItem productItem = request.toEntity(product, details);
        productItemRepository.save(productItem);

        product.addOptionGroup(productItem);
        productRepository.save(product);

        return generateProductResponse(product);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        return generateProductResponse(product);
    }

    public ProductResponse restock(Long productId, List<RestockRequest> requests) {
        for (RestockRequest request : requests) {
            ProductItem productItem = productItemRepository.findById(request.getProductItemId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

            if (!productItem.getProduct().getId().equals(productId)) {
                throw new IllegalArgumentException("요청한 상품과 재입고하려는 상품이 일치하지 않습니다.");
            }

            productItem.increaseStock(request.getQuantity());
            productItemRepository.save(productItem);
        }

        return getProduct(productId);
    }

    private ProductResponse generateProductResponse(Product product) {
        List<ProductOption> options = productOptionRepository.findAllByProduct(product);
        return ProductResponse.of(product, options);
    }
}
