package com.dutact.web.features.notification.subscription;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AccountSubscriptionRegistryImpl implements AccountSubscriptionRegistry {
    private final Map<String, SubscriptionInfo> subscriptions = new HashMap<>();
    private final Map<SubscriptionDeviceAccountIndex, String> deviceAccountIndices = new HashMap<>();
    private final Map<Integer, Set<String>> accountSubscriptions = new HashMap<>();

    @Override
    public String subscribe(String deviceId, Integer accountId) {
        var subscriptionId = new SubscriptionDeviceAccountIndex();
        subscriptionId.setDeviceId(deviceId);
        subscriptionId.setAccountId(accountId);

        if (deviceAccountIndices.containsKey(subscriptionId)) {
            return deviceAccountIndices.get(subscriptionId);
        }

        var token = UUID.randomUUID().toString();
        var subscriptionInfo = new SubscriptionInfo();
        subscriptionInfo.setDeviceId(deviceId);
        subscriptionInfo.setAccountId(accountId);
        subscriptionInfo.setSubscriptionToken(token);

        subscriptions.put(token, subscriptionInfo);
        deviceAccountIndices.put(subscriptionId, token);

        var accountSubscriptionsSet = accountSubscriptions.getOrDefault(accountId, new HashSet<>());
        accountSubscriptionsSet.add(token);

        return token;
    }

    @Override
    public void unsubscribe(String subscriptionToken) {
        var subscriptionInfo = subscriptions.get(subscriptionToken);
        if (subscriptionInfo == null) {
            return;
        }

        var subscriptionId = new SubscriptionDeviceAccountIndex();
        subscriptionId.setDeviceId(subscriptionInfo.getDeviceId());
        subscriptionId.setAccountId(subscriptionInfo.getAccountId());

        deviceAccountIndices.remove(subscriptionId);
        subscriptions.remove(subscriptionToken);

        var accountSubscriptionsSet = accountSubscriptions.get(subscriptionInfo.getAccountId());
        if (accountSubscriptionsSet != null) {
            accountSubscriptionsSet.remove(subscriptionToken);

            if (accountSubscriptionsSet.isEmpty()) {
                accountSubscriptions.remove(subscriptionInfo.getAccountId());
            }
        }
    }

    @Override
    public Collection<String> getSubscriptionTokens(Integer accountId) {
        return accountSubscriptions.getOrDefault(accountId, Set.of());
    }

    @Override
    public Collection<AccountSubscriptionInfo> getSubscriptions(Collection<Integer> accountIds) {
        return accountIds.stream()
                .map(accountId -> {
                    var subscriptionTokens = getSubscriptionTokens(accountId);
                    return subscriptionTokens.stream()
                            .map(subscriptions::get)
                            .toList();
                })
                .flatMap(Collection::stream)
                .map(subscriptionInfo -> {
                    var accountSubscriptionInfo = new AccountSubscriptionInfo();
                    accountSubscriptionInfo.setAccountId(subscriptionInfo.getAccountId());
                    accountSubscriptionInfo.setSubscriptionToken(subscriptionInfo.getSubscriptionToken());

                    return accountSubscriptionInfo;
                })
                .toList();
    }

    @Override
    public boolean subscriptionExists(String subscriptionToken) {
        return false;
    }
}

@Data
@EqualsAndHashCode
class SubscriptionDeviceAccountIndex {
    private String deviceId;
    private Integer accountId;
}

@Data
class SubscriptionInfo {
    private String deviceId;
    private Integer accountId;
    private String subscriptionToken;
}
