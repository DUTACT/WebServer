package com.dutact.web.specs;

import com.dutact.web.data.entity.auth.Account;
import com.dutact.web.data.entity.auth.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountSpecs {
    public static Specification<Account> emtpy() {
        return (root, query, cb) -> cb.conjunction();
    }

    public static Specification<Account> hasRole(Role role) {
        return (root, query, cb) -> cb.equal(root.get("role"), role);
    }

    public static Specification<Account> usernameContains(String username) {
        return (root, query, cb) -> cb.like(root.get("username"), "%" + username + "%");
    }
}
