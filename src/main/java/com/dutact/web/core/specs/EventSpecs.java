package com.dutact.web.core.specs;

import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.feedback.Feedback;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecs {
    private EventSpecs() {
    }

    public static Specification<Event> hasOrganizerId(Integer organizerId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("organizer").get("id"), organizerId);
    }

    public static Specification<Event> likeName(String text) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + text + "%");
    }

    public static Specification<Event> orderByCreatedAt(boolean asc) {
        return (root, query, criteriaBuilder) -> {
            assert query != null;
            query.orderBy(asc ? criteriaBuilder.asc(root.get("createdAt")) : criteriaBuilder.desc(root.get("createdAt")));
            return criteriaBuilder.conjunction();
        };
    }
}
