package com.dutact.web.core.entities;

import com.dutact.web.auth.factors.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "student")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student extends Account {
    private String fullName;
    private String phone;
    private String faculty;
    private String avatar_url;
}
