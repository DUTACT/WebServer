package com.dutact.web.features.notification.messaging.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SendFailedNotificationSpecs {
    public static Specification<SendFailedNotification> hasSubscriptionToken(String subscriptionToken) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("subscriptionToken"), subscriptionToken);
    }
}
