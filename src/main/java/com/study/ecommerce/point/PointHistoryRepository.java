package com.study.ecommerce.point;

import com.study.ecommerce.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    List<PointHistory> findAllByMemberIdOrderByCreatedAtDesc(Long memberId);

    Optional<PointHistory> findByOrder(Order order);
}
