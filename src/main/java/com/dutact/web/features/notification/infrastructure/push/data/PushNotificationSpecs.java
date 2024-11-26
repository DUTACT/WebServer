package com.dutact.web.features.notification.infrastructure.push.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PushNotificationSpecs {
    public static Specification<PushNotification> hasSubscriptionToken(String subscriptionToken) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("subscriptionToken"), subscriptionToken);
    }
}
