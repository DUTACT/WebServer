package com.dutact.web.core.entities;

import com.dutact.web.auth.Role;
import com.dutact.web.auth.UserCredential;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CTSV")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CTSV extends UserCredential {
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
        return Role.CTSV;
    }
}
