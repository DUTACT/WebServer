package com.dutact.web.core.specs;

import com.dutact.web.core.entities.EventFollow;
import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.eventregistration.EventRegistration;
import jakarta.persistence.criteria.Join;
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

    public static Specification<EventFollow> hasEventStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            Join<Event, EventFollow> eventsRegisteredJoin = root.join("event");
            return criteriaBuilder.equal(
                    criteriaBuilder.function(
                            "jsonb_extract_path_text",
                            String.class,
                            eventsRegisteredJoin.get("status"),
                            criteriaBuilder.literal("type")),
                    status);
        };
    }

    public static Specification<EventFollow> joinEvent() {
        return (root, query, criteriaBuilder) -> {
            root.fetch("event");
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<EventFollow> joinStudent() {
        return (root, query, criteriaBuilder) -> {
            root.fetch("student");
            return criteriaBuilder.conjunction();
        };
    }
}
