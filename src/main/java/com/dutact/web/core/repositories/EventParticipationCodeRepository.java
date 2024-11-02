package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.EventParticipationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EventParticipationCodeRepository extends
        JpaRepository<EventParticipationCode, Integer>, JpaSpecificationExecutor<EventParticipationCode> {
}
