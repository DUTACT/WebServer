package com.dutact.web.features.checkin.admin.services;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.core.repositories.EventCheckInRepository;
import com.dutact.web.core.specs.EventCheckInSpecs;
import com.dutact.web.features.checkin.admin.dtos.GetParticipationQueryParam;
import com.dutact.web.features.checkin.admin.dtos.ParticipationPreviewDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class EventCheckInServiceImpl implements EventCheckInService {
    private final EventCheckInRepository eventCheckInRepository;

    public EventCheckInServiceImpl(EventCheckInRepository eventCheckInRepository) {
        this.eventCheckInRepository = eventCheckInRepository;
    }

    @Override
    public PageResponse<ParticipationPreviewDto> getCheckedInParticipants(
            GetParticipationQueryParam queryParam) {

        var pageable = PageRequest.of(queryParam.getPage(), queryParam.getPageSize())
                .withSort(Sort.by(Sort.Order.asc("student.fullName")));
        var specs = EventCheckInSpecs.hasEventId(queryParam.getEventId());
        if (queryParam.getSearchQuery() != null) {
            specs = specs.and(EventCheckInSpecs.studentNameContains(queryParam.getSearchQuery()));
        }

        var page = eventCheckInRepository.findAll(specs, pageable);

        return PageResponse.of(page, ParticipationPreviewDto::from);
    }
}
