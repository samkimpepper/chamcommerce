package com.study.ecommerce.product.service;

import com.study.ecommerce.product.domain.*;
import com.study.ecommerce.product.dto.DetailCreateRequest;
import com.study.ecommerce.product.dto.OptionCreateRequest;
import com.study.ecommerce.product.dto.ProductCreateRequest;
import com.study.ecommerce.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductOptionDetailRepository productOptionDetailRepository;
    private final ProductOptionGroupRepository productOptionGroupRepository;

    public ProductResponse createProductInfo(ProductCreateRequest request) {
        Product product = request.toEntity();
        productRepository.save(product);

        for (OptionCreateRequest optionCreateRequest : request.getOptions()) {
            ProductOption productOption = optionCreateRequest.toEntity(product);

            for (DetailCreateRequest detailCreateRequest : optionCreateRequest.getDetails()) {
                ProductOptionDetail productOptionDetail = detailCreateRequest.toEntity(productOption);
                productOptionDetailRepository.save(productOptionDetail);

                productOption.addDetail(productOptionDetail);
            }
            productOptionRepository.save(productOption);
        }

        return ProductResponse.of(product);
    }
}
