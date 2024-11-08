package com.dutact.web.core.projections;

import lombok.Data;

@Data
public class CheckInPreview {
    private Integer studentId;
    private String studentName;
    private Long totalCheckIn;
}
