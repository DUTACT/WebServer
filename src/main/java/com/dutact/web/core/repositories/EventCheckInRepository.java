package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.EventCheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EventCheckInRepository
        extends JpaRepository<EventCheckIn, Integer>,
        JpaSpecificationExecutor<EventCheckIn> {
}
