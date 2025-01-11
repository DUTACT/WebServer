package com.dutact.web.data.repository;

import com.dutact.web.data.entity.feedback.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer>, JpaSpecificationExecutor<Feedback> {
    List<Feedback> findAllByEventId(Integer eventId);

    List<Feedback> findAllByEventIdIn(List<Integer> eventIds);

    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.event.organizer.id = :organizerId")
    long countByOrganizerId(@Param("organizerId") Integer organizerId);
}
