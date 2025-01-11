package com.dutact.web.specs;

import com.dutact.web.data.entity.event.Event;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;

public class EventSpecs {
    private EventSpecs() {
    }

    public static Specification<Event> hasId(Integer id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id);
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

    public static Specification<Event> hasStatus(String status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        criteriaBuilder.function(
                                "jsonb_extract_path_text",
                                String.class,
                                root.get("status"),
                                criteriaBuilder.literal("type")),
                        status);
    }

    public static Specification<Event> hasStatusIn(String[] statuses) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.in(
                        criteriaBuilder.function(
                                "jsonb_extract_path_text",
                                String.class,
                                root.get("status"),
                                criteriaBuilder.literal("type"))).value(Arrays.toString(statuses));
    }

    public static Specification<Event> joinOrganizer() {
        return (root, query, criteriaBuilder) -> {
            root.fetch("organizer");
            return criteriaBuilder.conjunction();
        };
    }
}
