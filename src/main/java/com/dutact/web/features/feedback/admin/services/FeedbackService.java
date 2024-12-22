package com.dutact.web.features.feedback.admin.services;

import com.dutact.web.features.feedback.admin.dtos.FeedbackDto;

import java.util.List;

public interface FeedbackService {
    List<FeedbackDto> getFeedbacks(Integer eventId);

    void deleteFeedback(Integer feedbackId);
}
