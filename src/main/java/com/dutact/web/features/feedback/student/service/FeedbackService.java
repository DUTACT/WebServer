package com.dutact.web.features.feedback.student.service;

import com.dutact.web.features.feedback.student.dtos.CreateFeedbackDto;
import com.dutact.web.features.feedback.student.dtos.FeedbackDto;
import com.dutact.web.features.feedback.student.dtos.FeedbackQueryParams;

import java.util.Collection;
import java.util.Optional;

public interface FeedbackService {
    FeedbackDto createFeedback(CreateFeedbackDto createFeedbackDto);

    Collection<FeedbackDto> getFeedbacks(FeedbackQueryParams feedbackQueryParams);

    Optional<FeedbackDto> getFeedback(Integer feedbackId);

    void deleteFeedback(Integer feedbackId);
}
