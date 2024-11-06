package com.dutact.web.features.checkin.admin.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ParticipationPreviewDto {
    @JsonProperty("studentId")
    private String studentId;

    @JsonProperty("studentName")
    private String studentName;

    @JsonProperty("totalCheckIn")
    private Integer totalCheckIn;
}
