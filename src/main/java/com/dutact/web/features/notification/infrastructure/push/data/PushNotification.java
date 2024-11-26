package com.dutact.web.features.notification.infrastructure.push.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "push_notification", schema = "notification")
@Getter
@Setter
public class PushNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @JsonProperty("message")
    private String message;

    @JsonProperty("subscription_token")
    private String subscriptionToken;
}
