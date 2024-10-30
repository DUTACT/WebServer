package com.dutact.web.core.specs;

import com.dutact.web.core.entities.EventRegistration;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

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

    public static Specification<EventRegistration> groupByDate(Integer offset) {
        return (root, query, criteriaBuilder) -> {
            // Create a date truncated field
            Expression<LocalDateTime> dateFrom3AM = criteriaBuilder.function("date_trunc", LocalDateTime.class,
                    criteriaBuilder.literal("day"),
                    criteriaBuilder.function("timestamp", LocalDateTime.class,
                            criteriaBuilder.function("date_add", LocalDateTime.class,
                                    root.get("eventTime"),
                                    criteriaBuilder.literal("INTERVAL '" + offset + " hours'")
                    )
            );

            // Grouping the results
            query.groupBy(dateFrom3AM);

            // Selecting the date and count of records
            query.select(criteriaBuilder.construct(GroupedEventDTO.class,
                    dateFrom3AM,
                    criteriaBuilder.count(root.get("id"))
            ));

            return criteriaBuilder.conjunction(); // No filtering criteria
        };
    }
}
