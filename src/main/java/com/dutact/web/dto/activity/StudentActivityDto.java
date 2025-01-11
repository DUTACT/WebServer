package com.dutact.web.dto.activity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudentActivityDto {
    private Integer id;
    private ActivityType type;
    private LocalDateTime createdAt;
    private Integer eventId;
    private String eventName;
    private Integer postId;
    private Integer feedbackId;
} 