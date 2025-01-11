package com.dutact.web.data.repository;

import com.dutact.web.data.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByUsername(String username);

    boolean existsByUsername(String username);
}
