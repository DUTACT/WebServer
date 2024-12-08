package com.dutact.web.features.notification.core.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationSpecs {
    public static Specification<Notification> hasAccountId(Integer accountId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("accountId"), accountId);
    }

    public static Specification<Notification> notExpired() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("expiryDate"), query.getCurrentTimestamp());
    }
}
