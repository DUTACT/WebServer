package com.dutact.web.core.specs;

import com.dutact.web.core.entities.EventOrganizer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrganizerSpecs {
    public static Specification<EventOrganizer> emtpy() {
        return (root, query, cb) -> cb.conjunction();
    }

    public static Specification<EventOrganizer> usernameContains(String username) {
        return (root, query, cb) -> cb.like(root.get("username"), "%" + username + "%");
    }
}
