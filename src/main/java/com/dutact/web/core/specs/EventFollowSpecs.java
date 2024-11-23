package com.dutact.web.core.specs;

import com.dutact.web.core.entities.EventFollow;
import org.springframework.data.jpa.domain.Specification;

public class EventFollowSpecs {
    private EventFollowSpecs() {
    }

    public static Specification<EventFollow> hasEventId(Integer eventId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("event").get("id"), eventId);
    }

    public static Specification<EventFollow> hasStudentId(Integer studentId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("student").get("id"), studentId);
    }

    public static Specification<EventFollow> joinEvent() {
        return (root, query, criteriaBuilder) -> {
            root.fetch("event");
            return null;
        };
    }
}
