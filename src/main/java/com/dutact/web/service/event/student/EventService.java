package com.dutact.web.service.event.student;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.dto.event.student.EventDetailsDto;
import com.dutact.web.dto.event.student.EventDto;
import com.dutact.web.dto.event.student.EventFollowDto;
import com.dutact.web.dto.event.student.EventRegisteredDto;
import com.dutact.web.dto.liker.StudentBasicInfoDto;
import com.dutact.web.service.event.student.exceptions.FollowForbiddenException;
import com.dutact.web.service.event.student.exceptions.RegisterForbiddenException;
import com.dutact.web.service.event.student.exceptions.UnfollowForbiddenException;
import com.dutact.web.service.event.student.exceptions.UnregisterForbiddenException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Optional<EventDto> getEvent(Integer id);

    List<EventDto> getEvents();

    EventRegisteredDto register(Integer eventId, Integer studentId)
            throws RegisterForbiddenException, NotExistsException, FollowForbiddenException;

    @Transactional
    void unregister(Integer eventId, Integer studentId)
            throws NotExistsException, UnregisterForbiddenException;

    EventFollowDto follow(Integer eventId, Integer studentId)
            throws FollowForbiddenException, NotExistsException;

    @Transactional
    void unfollow(Integer eventId, Integer studentId)
            throws NotExistsException, UnfollowForbiddenException;

    PageResponse<EventDetailsDto> getRegisteredEvents(Integer studentId, Integer page, Integer pageSize);

    PageResponse<EventDetailsDto> getFollowedEvents(Integer studentId, Integer page, Integer pageSize);

    PageResponse<EventDetailsDto> getConfirmedEvents(Integer studentId, Integer page, Integer pageSize);

    List<StudentBasicInfoDto> getEventFollowers(Integer eventId);

    List<StudentBasicInfoDto> getEventRegistrants(Integer eventId);
}
