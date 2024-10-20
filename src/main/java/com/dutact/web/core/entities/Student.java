package com.dutact.web.core.entities;

import com.dutact.web.auth.factors.Account;
import com.dutact.web.core.entities.feedback.Feedback;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student extends Account {
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @OneToMany(mappedBy = "student")
    @Builder.Default
    private List<EventRegistration> eventRegistrations = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    @Builder.Default
    private List<Feedback> feedbacks = new ArrayList<>();

    public Student(Integer id) {
        super(id);
    }
}
