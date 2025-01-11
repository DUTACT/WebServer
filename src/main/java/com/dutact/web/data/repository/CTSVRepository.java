package com.dutact.web.data.repository;

import com.dutact.web.data.entity.StudentAffairsOffice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CTSVRepository extends JpaRepository<StudentAffairsOffice, Integer> {
    Optional<StudentAffairsOffice> findByUsername(String fullName);

    boolean existsByUsername(String username);
}
