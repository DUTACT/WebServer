package com.dutact.web.features.event.student.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "StudentRegisterEventDto")
@Data
public class EventRegistrationDto {
    private Integer studentId;
}
