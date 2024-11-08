package com.dutact.web.core.projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckInPreview {
    private Integer studentId;
    private String studentName;
    private Long totalCheckIn;
}
