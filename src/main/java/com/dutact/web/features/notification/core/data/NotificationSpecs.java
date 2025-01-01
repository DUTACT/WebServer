package com.dutact.web.features.notification.core.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationSpecs {
    public static Specification<Notification> hasAccountId(Integer accountId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("accountId"), accountId);
    }

    public static Specification<Notification> notExpired() {
        var now = LocalDateTime.now();
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.isNull(root.get("expireAt")),
                        criteriaBuilder.greaterThan(root.get("expireAt"), now)
                );
    }

    public static Specification<Notification> notRead() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isFalse(root.get("isRead"));
    }
}
