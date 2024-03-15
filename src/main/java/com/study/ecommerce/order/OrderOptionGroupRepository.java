package com.study.ecommerce.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderOptionGroupRepository extends JpaRepository<OrderOptionGroup, Long> {
}
