package com.dutact.web.features.feedback.student.service;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.feedback.student.dtos.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FeedbackService {
    FeedbackDto createFeedback(Integer studentId, CreateFeedbackDtoV2 createFeedbackDtoV2) throws NotExistsException;

    FeedbackDto createFeedback(Integer studentId, CreateFeedbackDtoV1 createFeedbackDtoV1) throws NotExistsException;

    List<FeedbackDto> getFeedbacks(FeedbackQueryParams feedbackQueryParams);

    Optional<FeedbackDto> getFeedback(Integer feedbackId);

    FeedbackDto updateFeedback(Integer feedbackId, UpdateFeedbackDtoV2 updateFeedbackDtoV2) throws NotExistsException;

    FeedbackDto updateFeedback(Integer feedbackId, UpdateFeedbackDtoV1 updateFeedbackDtoV1) throws NotExistsException;

    void deleteFeedback(Integer feedbackId);

    void likeFeedback(Integer studentId, Integer feedbackId) throws NotExistsException;
    
    void unlikeFeedback(Integer studentId, Integer feedbackId) throws NotExistsException;
    
    Collection<FeedbackDto> getLikedFeedbacks(Integer studentId);
}
