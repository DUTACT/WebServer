package com.dutact.web.common.notification.core.data;

import com.dutact.web.common.notification.core.NotificationDetailsConverter;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification", schema = "notification")
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "details")
    @Convert(converter = NotificationDetailsConverter.class)
    private Object details;

    @Column(name = "notification_type")
    private String notificationType;

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Nullable
    @Column(name = "expire_at")
    private LocalDateTime expireAt;

    @Column(name = "is_read")
    private boolean isRead = false;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
