package com.dutact.web.common.notification.subscription.data;

import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public class AccountSubscriptionSpecs {
    private AccountSubscriptionSpecs() {
    }

    public static Specification<AccountSubscription> hasAccountId(Integer accountId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("accountId"), accountId);
    }

    public static Specification<AccountSubscription> hasAccountIdIn(Collection<Integer> accountIds) {
        return (root, query, criteriaBuilder) ->
                root.get("accountId").in(accountIds);
    }

    public static Specification<AccountSubscription> hasDeviceId(String deviceId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deviceId"), deviceId);
    }

}
