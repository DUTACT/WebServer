package com.dutact.web.core.specs;

import com.dutact.web.core.entities.eventchange.EventChange;
import org.springframework.data.jpa.domain.Specification;

public class EventChangeSpecs {
    private EventChangeSpecs() {
    }

    public static Specification<EventChange> hasEventId(Integer eventId) {
        return (root, query, cb) -> cb.equal(root.get("event").get("id"), eventId);
    }
}
