package com.dutact.web.features.event.student.services;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.event.student.dtos.*;
import com.dutact.web.features.event.student.services.exceptions.FollowForbiddenException;
import com.dutact.web.features.event.student.services.exceptions.RegisterForbiddenException;
import com.dutact.web.features.event.student.services.exceptions.UnfollowForbiddenException;
import com.dutact.web.features.event.student.services.exceptions.UnregisterForbiddenException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Optional<EventDto> getEvent(Integer id);

    List<EventDto> getEvents();

    EventRegisteredDto register(Integer eventId, Integer studentId)
            throws RegisterForbiddenException, NotExistsException;

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
}
