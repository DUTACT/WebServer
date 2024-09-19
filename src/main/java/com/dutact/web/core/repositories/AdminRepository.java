package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByUsername(String username);
    boolean existsByUsername(String username);
}
