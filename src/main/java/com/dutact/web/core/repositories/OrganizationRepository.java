package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.EventOrganizer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<EventOrganizer, Integer> {

    Optional<EventOrganizer> findByUsername(String username);
    boolean existsByUsername(String username);
}
