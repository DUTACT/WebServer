package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.eventchange.EventChange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventChangeRepository extends JpaRepository<EventChange, Integer> {
}
