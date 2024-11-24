package com.dutact.web.features.notification.subscription.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "account_subscription", schema = "notification")
public class AccountSubscription {
    @Id
    @Column(name = "subscription_token")
    private String subscriptionToken;

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "device_id")
    private String deviceId;
}
