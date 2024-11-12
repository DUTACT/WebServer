package com.dutact.web.core.projections;

import com.dutact.web.core.entities.eventregistration.participationcert.ParticipationCertificateStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckInPreview {
    private Integer studentId;
    private String studentName;
    private String studentAvatarUrl;
    private Long totalCheckIn;
    private ParticipationCertificateStatus certificateStatus;
}
