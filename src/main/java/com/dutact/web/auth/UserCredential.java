package com.dutact.web.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usercredential")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UserCredential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    public abstract Role getRole();
}
