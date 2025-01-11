package com.dutact.web.dto.analytics;

import com.dutact.web.dto.analytics.registration.EventRegistrationCountByDateDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RegistrationAndParticipationCountDto {
    private List<EventRegistrationCountByDateDto> registrations;
    private List<EventRegistrationCountByDateDto> participations;
}
