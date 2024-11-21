package com.dutact.web.features.notification.subscription;

import java.util.Collection;

public interface AccountSubscriptionRegistry {
    /**
     * @return subscription token
     */
    String subscribe(String deviceId, Integer accountId);

    void unsubscribe(String subscriptionToken);

    Collection<String> getSubscriptionTokens(Integer accountId);

    Collection<AccountSubscriptionInfo> getSubscriptions(Collection<Integer> accountIds);

    boolean subscriptionExists(String subscriptionToken);
}
