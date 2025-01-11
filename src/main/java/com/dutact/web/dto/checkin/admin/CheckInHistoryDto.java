package com.dutact.web.dto.checkin.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CheckInHistoryDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("checkInTime")
    private LocalDateTime checkInTime;

    @JsonProperty("checkInCode")
    private CheckInCodeInfo checkInCode;
}
