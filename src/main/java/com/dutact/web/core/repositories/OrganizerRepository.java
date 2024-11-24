package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.EventOrganizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OrganizerRepository extends JpaRepository<EventOrganizer, Integer>, JpaSpecificationExecutor<EventOrganizer> {

    Optional<EventOrganizer> findByUsername(String username);

    boolean existsByUsername(String username);
}
