package com.study.ecommerce.product.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionDetailRepository extends JpaRepository<ProductOptionDetail, Long> {
}
