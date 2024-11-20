package com.dutact.web.features.newsfeed.student.service;

import com.dutact.web.features.newsfeed.student.dtos.NewsfeedItemDto;

import java.util.List;

public interface NewsfeedService {
    List<NewsfeedItemDto> getFollowedNewsfeed(Integer studentId);
    List<NewsfeedItemDto> getLikedNewsfeed(Integer studentId);
}
