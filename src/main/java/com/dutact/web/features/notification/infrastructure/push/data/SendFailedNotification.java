package com.dutact.web.features.notification.infrastructure.push.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "send_failed_notification", schema = "notification")
public class SendFailedNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "subscription_token")
    private String subscriptionToken;

    @Column(name = "message")
    private String message;

    @Column(name = "retries")
    private Integer retries = 0;
}
