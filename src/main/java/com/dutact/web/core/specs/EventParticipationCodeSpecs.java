package com.dutact.web.core.specs;

import com.dutact.web.core.entities.EventParticipationCode;
import org.springframework.data.jpa.domain.Specification;

public class EventParticipationCodeSpecs {
    private EventParticipationCodeSpecs() {
    }

    public static Specification<EventParticipationCode> hasEventId(Integer eventId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("event").get("id"), eventId);
    }
}
