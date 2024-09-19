package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.CTSV;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CTSVRepository extends JpaRepository<CTSV, Integer> {
    Optional<CTSV> findByUsername(String fullName);
    boolean existsByUsername(String username);
}
