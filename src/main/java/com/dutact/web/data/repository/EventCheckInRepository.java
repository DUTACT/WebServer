package com.dutact.web.data.repository;

import com.dutact.web.data.entity.EventCheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EventCheckInRepository
        extends JpaRepository<EventCheckIn, Integer>,
        JpaSpecificationExecutor<EventCheckIn> {
}
