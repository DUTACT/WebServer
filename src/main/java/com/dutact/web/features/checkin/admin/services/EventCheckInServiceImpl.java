package com.dutact.web.features.checkin.admin.services;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.core.repositories.views.CheckInQueryParams;
import com.dutact.web.core.repositories.views.CheckInViewsRepository;
import com.dutact.web.features.checkin.admin.dtos.CheckInPreviewDto;
import org.springframework.stereotype.Service;

@Service("AdminEventCheckInService")
public class EventCheckInServiceImpl implements EventCheckInService {
    private final CheckInViewsRepository checkInViewsRepository;
    private final EventCheckInMapper mapper;

    public EventCheckInServiceImpl(CheckInViewsRepository checkInViewsRepository,
                                   EventCheckInMapper mapper) {
        this.checkInViewsRepository = checkInViewsRepository;
        this.mapper = mapper;
    }

    @Override
    public PageResponse<CheckInPreviewDto> getCheckedInParticipants(
            CheckInQueryParams queryParam) {

        var page = checkInViewsRepository.getCheckInPreviews(queryParam);

        return PageResponse.of(page, mapper::toDto);
    }
}
