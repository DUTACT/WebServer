package com.dutact.web.features.notification.subscription;

import java.util.Collection;

public interface AccountSubscriptionRegistry {
    String subscribe(String subscriptionToken, Integer accountId);

    void unsubscribe(String subscriptionToken);

    Collection<Integer> getSubscriptionTokens(String accountId);

    Collection<AccountSubscriptionInfo> getSubscriptions(Collection<Integer> accountIds);
}
