package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.event.Event;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    boolean existsByIdAndOrganizerId(Integer eventId, Integer orgId);

    @Query("SELECT COUNT(e) FROM Event e WHERE e.organizer.id = :organizerId")
    long countByOrganizerId(@Param("organizerId") Integer organizerId);
}