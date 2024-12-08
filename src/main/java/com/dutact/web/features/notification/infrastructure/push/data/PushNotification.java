package com.dutact.web.features.notification.infrastructure.push.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "push_notification", schema = "notification")
@Getter
@Setter
public class PushNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonProperty("message")
    private String message;

    @JsonProperty("subscription_token")
    private String subscriptionToken;

    @Column(name = "expire_at")
    private LocalDateTime expireAt;
}
