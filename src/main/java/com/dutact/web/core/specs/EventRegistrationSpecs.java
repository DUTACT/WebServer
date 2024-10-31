package com.dutact.web.core.specs;

import com.dutact.web.core.entities.EventRegistration;
import org.springframework.data.jpa.domain.Specification;

public class EventRegistrationSpecs {
    private EventRegistrationSpecs() {
    }

    public static Specification<EventRegistration> hasEventId(Integer eventId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("event").get("id"), eventId);
    }

    public static Specification<EventRegistration> hasStudentId(Integer studentId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("student").get("id"), studentId);
    }
}
