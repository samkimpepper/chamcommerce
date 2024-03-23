package com.study.ecommerce.order.repository;

import com.study.ecommerce.order.domain.SellerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerOrderRepository extends JpaRepository<SellerOrder, Long> {
}
