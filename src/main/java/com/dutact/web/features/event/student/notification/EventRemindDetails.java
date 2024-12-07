package com.dutact.web.features.event.student.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
class EventRemindDetails {
    @JsonProperty("eventId")
    private Integer eventId;

    @JsonProperty("type")
    private String type;
}
