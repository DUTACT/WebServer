package com.dutact.web.features.checkin.admin.services;

import com.dutact.web.core.repositories.EventCheckInRepository;
import com.dutact.web.core.specs.EventCheckInSpecs;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class EventCheckInServiceImpl implements EventCheckInService {
    private final EventCheckInRepository eventCheckInRepository;

    public EventCheckInServiceImpl(EventCheckInRepository eventCheckInRepository) {
        this.eventCheckInRepository = eventCheckInRepository;
    }

    @Override
    public List<String> getCheckedInParticipants(Integer eventId) {
        var pagable = PageRequest.of(0, 100)
                .withSort(PageRequest.Sort.by("checkInTime").descending());

        var page = eventCheckInRepository.findAll(EventCheckInSpecs.hasStudent(1), pagable);
        page.
    }
}
