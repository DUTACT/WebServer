package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.event.Event;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    boolean existsByIdAndOrganizerId(Integer eventId, Integer orgId);

    Collection<Event> findAllDesc();
    Collection<Event> findAllByOrganizerIdOrderByCreatedAtDesc(Integer orgId);
    Collection<Event> findAllByNameOrderByCreatedAtDesc(String name);

    @Query(value = "SELECT * FROM Event e WHERE e.status ->> 'type' = :status", nativeQuery = true)
    List<Event> findAllByStatus(String status);
}