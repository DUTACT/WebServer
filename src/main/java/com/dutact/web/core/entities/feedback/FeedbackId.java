package com.dutact.web.core.entities.feedback;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FeedbackId {
    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "event_id")
    private Integer eventId;
}
