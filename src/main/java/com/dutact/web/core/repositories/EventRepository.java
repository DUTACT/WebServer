package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    boolean existsByIdAndOrganizerId(Integer eventId, Integer orgId);

    Collection<Event> findAllByOrganizerId(Integer orgId);
}