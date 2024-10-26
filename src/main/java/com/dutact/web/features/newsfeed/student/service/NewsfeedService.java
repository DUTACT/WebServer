package com.dutact.web.features.newsfeed.student.service;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.feedback.student.dtos.CreateFeedbackDto;
import com.dutact.web.features.feedback.student.dtos.FeedbackDto;
import com.dutact.web.features.feedback.student.dtos.FeedbackQueryParams;
import com.dutact.web.features.feedback.student.dtos.UpdateFeedbackDto;
import com.dutact.web.features.newsfeed.student.dtos.NewsfeedElementDto;

import java.util.List;
import java.util.Optional;

public interface NewsfeedService {
    List<NewsfeedElementDto> getFollowedNewsfeed(Integer studentId);
}
