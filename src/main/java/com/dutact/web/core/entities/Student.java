package com.dutact.web.core.entities;

import com.dutact.web.auth.factors.Account;
import com.dutact.web.core.entities.common.UploadFileConverter;
import com.dutact.web.core.entities.common.UploadedFile;
import com.dutact.web.core.entities.feedback.Feedback;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

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

    @Nullable
    @Column(name = "avatar", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = UploadFileConverter.class)
    private UploadedFile avatar;

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
