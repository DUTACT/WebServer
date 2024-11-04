package com.dutact.web.core.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_check_in")
@Getter
@Setter
public class EventCheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "check_in_code_id")
    private EventCheckInCode checkInCode;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;
}
