package com.dutact.web.service.newsfeed;

import com.dutact.web.dto.newsfeed.NewsfeedItemDto;

import java.util.List;

public interface NewsfeedService {
    List<NewsfeedItemDto> getFollowedNewsfeed(Integer studentId);

    List<NewsfeedItemDto> getLikedNewsfeed(Integer studentId);
}
