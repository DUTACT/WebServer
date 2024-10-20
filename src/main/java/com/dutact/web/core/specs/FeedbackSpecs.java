package com.dutact.web.core.specs;

import com.dutact.web.core.entities.feedback.Feedback;
import org.springframework.data.jpa.domain.Specification;

public class FeedbackSpecs {
    public static Specification<Feedback> hasStudentId(Integer studentId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("student").get("id"), studentId);
    }

    public static Specification<Feedback> hasEventId(Integer eventId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("event").get("id"), eventId);
    }

    public static Specification<Feedback> orderByPostedAt(boolean asc) {
        return (root, query, criteriaBuilder) -> {
            assert query != null;
            query.orderBy(asc ? criteriaBuilder.asc(root.get("postedAt")) : criteriaBuilder.desc(root.get("postedAt")));
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Feedback> joinStudent() {
        return (root, query, criteriaBuilder) -> {
            root.fetch("student");
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Feedback> joinEvent() {
        return (root, query, criteriaBuilder) -> {
            root.fetch("event");
            return criteriaBuilder.conjunction();
        };
    }
}
