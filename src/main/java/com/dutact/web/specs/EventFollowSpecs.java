package com.dutact.web.specs;

import com.dutact.web.data.entity.EventFollow;
import com.dutact.web.data.entity.event.Event;
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
            root.join("event");
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<EventFollow> joinStudent() {
        return (root, query, criteriaBuilder) -> {
            root.join("student");
            return criteriaBuilder.conjunction();
        };
    }
}
