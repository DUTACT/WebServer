package com.dutact.web.core.entities;

import com.dutact.web.auth.Role;
import com.dutact.web.auth.UserCredential;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends UserCredential {
    @Override
    public Role getRole() {
        return Role.ADMIN;
    }
}
