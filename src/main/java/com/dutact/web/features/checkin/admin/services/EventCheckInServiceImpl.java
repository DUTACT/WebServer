package com.dutact.web.features.checkin.admin.services;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.entities.eventregistration.EventRegistration;
import com.dutact.web.core.repositories.EventCheckInRepository;
import com.dutact.web.core.repositories.EventRegistrationRepository;
import com.dutact.web.core.repositories.views.CheckInQueryParams;
import com.dutact.web.core.repositories.views.CheckInViewsRepository;
import com.dutact.web.core.specs.EventCheckInSpecs;
import com.dutact.web.features.checkin.admin.dtos.CheckInDetailDto;
import com.dutact.web.features.checkin.admin.dtos.CheckInPreviewDto;
import org.springframework.stereotype.Service;

@Service("AdminEventCheckInService")
public class EventCheckInServiceImpl implements EventCheckInService {
    private final EventCheckInRepository checkInRepository;
    private final EventRegistrationRepository registrationRepository;
    private final CheckInViewsRepository checkInViewsRepository;
    private final EventCheckInMapper mapper;

    public EventCheckInServiceImpl(EventCheckInRepository checkInRepository,
                                   EventRegistrationRepository registrationRepository,
                                   CheckInViewsRepository checkInViewsRepository,
                                   EventCheckInMapper mapper) {
        this.checkInRepository = checkInRepository;
        this.registrationRepository = registrationRepository;
        this.checkInViewsRepository = checkInViewsRepository;
        this.mapper = mapper;
    }

    @Override
    public PageResponse<CheckInPreviewDto> getCheckedInParticipants(
            CheckInQueryParams queryParam) {

        var page = checkInViewsRepository.getCheckInPreviews(queryParam);

        return PageResponse.of(page, mapper::toDto);
    }

    @Override
    public CheckInDetailDto getCheckInDetail(Integer eventId, Integer studentId) 
            throws NotExistsException {
        EventRegistration registration = registrationRepository
                .findByEventIdAndStudentId(eventId, studentId)
                .orElseThrow(() -> new NotExistsException("Student registration not found"));

        var checkIns = checkInRepository.findAll(
                EventCheckInSpecs.hasStudent(studentId)
                        .and(EventCheckInSpecs.hasEventId(eventId)));

        var detail = new CheckInDetailDto();
        detail.setStudentId(studentId);
        detail.setStudentName(registration.getStudent().getFullName());
        detail.setTotalCheckIn(checkIns.size());
        detail.setCertificateStatus(registration.getCertificateStatus());
        detail.setCheckIns(checkIns.stream()
                .map(mapper::toCheckInHistoryDto)
                .toList());

        return detail;
    }
}
