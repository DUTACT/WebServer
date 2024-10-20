package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.feedback.Feedback;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    boolean existsByIdAndOrganizerId(Integer eventId, Integer orgId);
    @Query(value = "SELECT * FROM Event e WHERE e.status ->> 'type' = :status", nativeQuery = true)
    List<Event> findAllByStatus(String status);
}