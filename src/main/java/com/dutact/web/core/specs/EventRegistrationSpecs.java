package com.dutact.web.core.specs;

import com.dutact.web.core.entities.EventCheckIn;
import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.eventregistration.EventRegistration;
import com.dutact.web.core.entities.feedback.Feedback;
import jakarta.persistence.criteria.Join;
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

    public static Specification<EventRegistration> hasStudentIds(Integer[] studentIds) {
        return (root, query, criteriaBuilder) ->
                root.get("student").get("id").in((Object[]) studentIds);
    }

    public static Specification<EventRegistration> hasCertificateStatus(String certificateStatus) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        criteriaBuilder.function(
                                "jsonb_extract_path_text",
                                String.class,
                                root.get("certificateStatus"),
                                criteriaBuilder.literal("type")),
                        certificateStatus);
    }

    public static Specification<EventRegistration> hasEventStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            Join<Event, EventRegistration> eventsRegisteredJoin = root.join("event");
            return criteriaBuilder.equal(
                    criteriaBuilder.function(
                            "jsonb_extract_path_text",
                            String.class,
                            eventsRegisteredJoin.get("status"),
                            criteriaBuilder.literal("type")),
                    status);
        };
    }

    public static Specification<EventRegistration> checkedInAtLeast(int times) {
        return (root, query, criteriaBuilder) -> {
            assert query != null;
            var subquery = query.subquery(Long.class);
            var subRoot = subquery.from(EventCheckIn.class);
            subquery.select(criteriaBuilder.count(subRoot.get("id")));

            var studentIdMatch = criteriaBuilder
                    .equal(subRoot.get("student").get("id"),
                            root.get("student").get("id"));

            var eventIdMatch = criteriaBuilder
                    .equal(subRoot.get("checkInCode").get("event").get("id"),
                            root.get("event").get("id"));

            subquery.where(
                    studentIdMatch,
                    eventIdMatch
            );

            return query.where(
                    criteriaBuilder.greaterThanOrEqualTo(
                            subquery,
                            (long) times
                    )
            ).getRestriction();
        };
    }

    public static Specification<EventRegistration> joinEvent() {
        return (root, query, criteriaBuilder) -> {
            root.join("event");
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<EventRegistration> joinStudent() {
        return (root, query, criteriaBuilder) -> {
            root.join("student");
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<EventRegistration> joinOrganizer() {
        return (root, query, criteriaBuilder) -> {
            root.join("event").join("organizer");
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<EventRegistration> orderByRegisteredAt(boolean asc) {
        return (root, query, criteriaBuilder) -> {
            assert query != null;
            query.orderBy(asc ? criteriaBuilder.asc(root.get("registeredAt")) : criteriaBuilder.desc(root.get("registeredAt")));
            return criteriaBuilder.conjunction();
        };
    }
}
