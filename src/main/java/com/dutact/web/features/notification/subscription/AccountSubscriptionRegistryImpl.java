package com.dutact.web.features.notification.subscription;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class AccountSubscriptionRegistryImpl implements AccountSubscriptionRegistry {
    @Override
    public String subscribe(String deviceId, Integer accountId) {
        return "";
    }

    @Override
    public void unsubscribe(String subscriptionToken) {

    }

    @Override
    public Collection<String> getSubscriptionTokens(Integer accountId) {
        return List.of();
    }

    @Override
    public Collection<AccountSubscriptionInfo> getSubscriptions(Collection<Integer> accountIds) {
        return List.of();
    }

    @Override
    public boolean subscriptionExists(String subscriptionToken) {
        return false;
    }
}
