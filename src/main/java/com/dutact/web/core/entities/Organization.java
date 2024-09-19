package com.dutact.web.core.entities;

import com.dutact.web.auth.Role;
import com.dutact.web.auth.UserCredential;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "organization")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organization extends UserCredential {
    private String name;
    private String avatar;

    @Column(name = "enabled")
    private boolean enabled = true;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }

    @Override
    public Role getRole() {
        return Role.ORGANIZATION;
    }
}
