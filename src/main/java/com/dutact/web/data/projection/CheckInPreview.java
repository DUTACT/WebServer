package com.dutact.web.data.projection;

import com.dutact.web.data.entity.eventregistration.participationcert.ParticipationCertificateStatus;
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
