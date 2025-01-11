package com.dutact.web.common.notification.subscription;

import com.dutact.web.common.notification.subscription.data.AccountSubscription;
import com.dutact.web.common.notification.subscription.data.AccountSubscriptionRepository;
import com.dutact.web.common.notification.subscription.data.AccountSubscriptionSpecs;
import com.dutact.web.service.auth.AccountService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountSubscriptionHandlerImpl implements AccountSubscriptionHandler {
    private final AccountSubscriptionRepository subscriptionRepository;
    private final AccountService accountService;

    public AccountSubscriptionHandlerImpl(AccountSubscriptionRepository subscriptionRepository,
                                          AccountService accountService) {
        this.subscriptionRepository = subscriptionRepository;
        this.accountService = accountService;
    }

    @Override
    public String subscribe(String deviceId, Integer accountId) {
        var existingSubscription = subscriptionRepository.findOne(AccountSubscriptionSpecs.hasAccountId(accountId)
                .and(AccountSubscriptionSpecs.hasDeviceId(deviceId)));

        if (existingSubscription.isPresent()) {
            return existingSubscription.get().getSubscriptionToken();
        }

        var token = UUID.randomUUID().toString();
        var subscription = new AccountSubscription();
        subscription.setDeviceId(deviceId);
        subscription.setAccountId(accountId);
        subscription.setSubscriptionToken(token);

        subscriptionRepository.save(subscription);

        return token;
    }

    @Override
    public void unsubscribe(String subscriptionToken) {
        subscriptionRepository.deleteById(subscriptionToken);
    }
}