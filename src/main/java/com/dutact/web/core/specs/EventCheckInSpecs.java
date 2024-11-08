package com.dutact.web.core.specs;

import com.dutact.web.core.entities.EventCheckIn;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class EventCheckInSpecs {
    private EventCheckInSpecs() {
    }

    public static Specification<EventCheckIn> hasCode(UUID code) {
        return (root, query, cb) -> cb.equal(root.get("checkInCode").get("id"), code);
    }

    public static Specification<EventCheckIn> hasStudent(Integer studentId) {
        return (root, query, cb) -> cb.equal(root.get("student").get("id"), studentId);
    }

    public static Specification<EventCheckIn> hasEventId(Integer eventId) {
        return (root, query, cb) -> cb.equal(root.get("event").get("id"), eventId);
    }

    public static Specification<EventCheckIn> studentNameContains(String name) {
        return (root, query, cb) -> cb.like(root.get("student").get("fullName"), "%" + name + "%");
    }
}
