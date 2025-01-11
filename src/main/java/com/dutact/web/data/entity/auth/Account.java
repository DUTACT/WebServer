package com.dutact.web.data.entity.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Account(Integer id) {
        this.id = id;
    }
}
