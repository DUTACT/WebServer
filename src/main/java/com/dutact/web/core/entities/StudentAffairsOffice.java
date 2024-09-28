package com.dutact.web.core.entities;

import com.dutact.web.auth.factors.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "student_affairs_office")
@Getter
@Setter
public class StudentAffairsOffice extends Account {
}
