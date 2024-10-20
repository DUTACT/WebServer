package com.dutact.web.features.feedback.student.service;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.feedback.student.dtos.CreateFeedbackDto;
import com.dutact.web.features.feedback.student.dtos.FeedbackDto;
import com.dutact.web.features.feedback.student.dtos.FeedbackQueryParams;
import com.dutact.web.features.feedback.student.dtos.UpdateFeedbackDto;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {
    FeedbackDto createFeedback(Integer studentId, CreateFeedbackDto createFeedbackDto) throws NotExistsException;

    List<FeedbackDto> getFeedbacks(FeedbackQueryParams feedbackQueryParams);

    Optional<FeedbackDto> getFeedback(Integer feedbackId);

    FeedbackDto updateFeedback(Integer feedbackId, UpdateFeedbackDto updateFeedbackDto) throws NotExistsException;

    void deleteFeedback(Integer feedbackId);
}
