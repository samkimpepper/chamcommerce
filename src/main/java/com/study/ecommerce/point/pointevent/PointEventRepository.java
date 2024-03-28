package com.study.ecommerce.point.pointevent;

import com.study.ecommerce.point.pointevent.domain.PointEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PointEventRepository extends JpaRepository<PointEvent, Long> {

    List<PointEvent> findByStartedAtBeforeAndEndedAtAfter(LocalDateTime now1, LocalDateTime now2);
}
