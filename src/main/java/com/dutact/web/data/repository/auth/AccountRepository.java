package com.dutact.web.data.repository.auth;

import com.dutact.web.data.entity.auth.Account;
import com.dutact.web.data.entity.auth.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {
    Optional<Account> findByUsername(String username);

    boolean existsByUsername(String username);

    Page<Account> findAllByRole(Role role, Pageable pageable);
}
