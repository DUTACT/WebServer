package com.dutact.web.common.notification.infrastructure.push.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PushNotificationSpecs {
    public static Specification<PushNotification> hasSubscriptionToken(String subscriptionToken) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("subscriptionToken"), subscriptionToken);
    }

    public static Specification<PushNotification> hasExpireAtAfter(LocalDateTime time) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("expireAt"), time);
    }
}
