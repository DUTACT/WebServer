package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

    Optional<Organization> findByUsername(String username);
    boolean existsByUsername(String username);
}
