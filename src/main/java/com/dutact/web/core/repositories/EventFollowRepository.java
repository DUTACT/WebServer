package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.EventFollow;
import com.dutact.web.core.entities.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventFollowRepository extends JpaRepository<EventFollow, Integer> {
    boolean existsByEventIdAndStudentId(Integer eventId, Integer studentId);

    void deleteByEventIdAndStudentId(Integer eventId, Integer studentId);
}
