package com.dutact.web.core.specs;

import com.dutact.web.core.entities.Student;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudentSpecs {
    public static Specification<Student> emtpy() {
        return (root, query, cb) -> cb.conjunction();
    }

    public static Specification<Student> usernameContains(String username) {
        return (root, query, cb) -> cb.like(root.get("username"), "%" + username + "%");
    }
}
