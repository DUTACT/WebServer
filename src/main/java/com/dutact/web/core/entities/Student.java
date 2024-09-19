package com.dutact.web.core.entities;

import com.dutact.web.auth.Role;
import com.dutact.web.auth.UserCredential;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "student")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student extends UserCredential {
    private String name;
    private String phone;
    private String major;
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
        return Role.STUDENT;
    }
}
