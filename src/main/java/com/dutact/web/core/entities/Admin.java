package com.dutact.web.core.entities;

import com.dutact.web.auth.Role;
import com.dutact.web.auth.UserCredential;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
