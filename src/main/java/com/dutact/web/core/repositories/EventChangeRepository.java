package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.eventchange.EventChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EventChangeRepository extends JpaRepository<EventChange, Integer>,
        JpaSpecificationExecutor<EventChange> {
}
