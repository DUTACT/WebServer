package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.StudentAffairsOffice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CTSVRepository extends JpaRepository<StudentAffairsOffice, Integer> {
    Optional<StudentAffairsOffice> findByUsername(String fullName);
    boolean existsByUsername(String username);
}
