package com.dutact.web.core.specs;

import com.dutact.web.core.entities.checkincode.EventCheckInCode;
import org.springframework.data.jpa.domain.Specification;

public class EventCheckInCodeSpecs {
    private EventCheckInCodeSpecs() {
    }

    public static Specification<EventCheckInCode> hasEventId(Integer eventId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("event").get("id"), eventId);
    }
}
