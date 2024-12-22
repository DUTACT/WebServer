package com.dutact.web.features.feedback.admin.services;

import com.dutact.web.core.repositories.FeedbackRepository;
import com.dutact.web.features.feedback.admin.dtos.FeedbackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("adminFeedbackService")
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    @Override
    public List<FeedbackDto> getFeedbacks(Integer eventId) {
        return feedbackRepository.findAllByEventId(eventId).stream()
                .map(feedbackMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFeedback(Integer feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }
}
