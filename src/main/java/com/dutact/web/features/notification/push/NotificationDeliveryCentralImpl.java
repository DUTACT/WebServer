package com.dutact.web.features.notification.push;

import com.dutact.web.common.mapper.ObjectMapperUtils;
import com.dutact.web.features.notification.subscription.data.AccountSubscription;
import com.dutact.web.features.notification.subscription.data.AccountSubscriptionRepository;
import com.dutact.web.features.notification.subscription.data.AccountSubscriptionSpecs;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class NotificationDeliveryCentralImpl implements NotificationDeliveryCentral {
    private final AccountSubscriptionRepository subscriptionRepository;
    private final PushNotificationWorker pushNotificationWorker;

    public NotificationDeliveryCentralImpl(AccountSubscriptionRepository subscriptionRepository,
                                           PushNotificationWorker pushNotificationWorker) {
        this.subscriptionRepository = subscriptionRepository;
        this.pushNotificationWorker = pushNotificationWorker;
    }

    @SneakyThrows
    @Override
    public void sendNotification(Collection<Integer> accountIds, Object details, String notificationType) {
        var subscriptionTokens = subscriptionRepository
                .findAll(AccountSubscriptionSpecs.hasAccountIdIn(accountIds))
                .stream().map(AccountSubscription::getSubscriptionToken).toList();
        var message = new PushNotificationMessage();
        message.setDetails(details);
        message.setNotificationType(notificationType);

        var objectMapper = ObjectMapperUtils.createObjectMapper();
        var messageJson = objectMapper.writeValueAsString(message);

        pushNotificationWorker.sendNotification(subscriptionTokens, messageJson);
    }

    @Data
    static class PushNotificationMessage {
        @JsonProperty("details")
        private Object details;

        @JsonProperty("notificationType")
        private String notificationType;
    }
}
