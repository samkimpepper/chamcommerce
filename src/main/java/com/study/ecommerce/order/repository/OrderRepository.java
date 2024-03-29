package com.study.ecommerce.order.repository;

import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.order.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM orders o JOIN FETCH o.orderItems WHERE o.id = :id")
    Optional<Order> findByIdWithOrderItems(@Param("id") Long id);

    List<Order> findByCustomerIdAndOrderedAtBetweenAndStatus(Long memberId, LocalDateTime start, LocalDateTime end, OrderStatus status);
}
