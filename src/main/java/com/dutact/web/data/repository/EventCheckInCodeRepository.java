package com.dutact.web.data.repository;

import com.dutact.web.data.entity.checkincode.EventCheckInCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface EventCheckInCodeRepository extends
        JpaRepository<EventCheckInCode, UUID>, JpaSpecificationExecutor<EventCheckInCode> {
}
