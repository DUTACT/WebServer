package com.dutact.web.features.analytics.admin.dtos;

import com.dutact.web.features.analytics.admin.dtos.registration.EventRegistrationCountByDateDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RegistrationAndParticipationCountDto {
    private List<EventRegistrationCountByDateDto> registrations;
    private List<EventRegistrationCountByDateDto> participations;
}
