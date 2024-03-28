package com.study.ecommerce.point.pointevent;

import com.study.ecommerce.point.pointevent.domain.EventParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventParticipationRepository extends JpaRepository<EventParticipation, Long> {
    Optional<EventParticipation> findByMemberIdAndEventId(Long memberId, Long eventId);

    boolean existsByMemberIdAndEventId(Long memberId, Long eventId);
}
