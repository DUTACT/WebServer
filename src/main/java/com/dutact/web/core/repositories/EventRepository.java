package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.event.Event;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    boolean existsByIdAndOrganizerId(Integer eventId, Integer orgId);
}