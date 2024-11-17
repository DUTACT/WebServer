package com.dutact.web.auth.factors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
    boolean existsByUsername(String username);
    Page<Account> findAllByRole(Role role, Pageable pageable);
}
