package com.dutact.web.data.repository;

import com.dutact.web.data.entity.eventchange.EventChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EventChangeRepository extends JpaRepository<EventChange, Integer>,
        JpaSpecificationExecutor<EventChange> {
}
