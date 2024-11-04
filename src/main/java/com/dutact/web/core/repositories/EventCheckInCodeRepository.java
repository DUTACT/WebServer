package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.EventCheckInCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface EventCheckInCodeRepository extends
        JpaRepository<EventCheckInCode, UUID>, JpaSpecificationExecutor<EventCheckInCode> {
}
