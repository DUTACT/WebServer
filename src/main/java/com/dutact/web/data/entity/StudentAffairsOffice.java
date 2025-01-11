package com.dutact.web.data.entity;

import com.dutact.web.data.entity.auth.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "student_affairs_office")
@Getter
@Setter
public class StudentAffairsOffice extends Account {
}
