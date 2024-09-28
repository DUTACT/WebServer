package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByUsername(String email);
    boolean existsByUsername(String email);
}
