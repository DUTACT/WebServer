package com.dutact.web.features.feedback.admin.controllers;

import com.dutact.web.features.feedback.admin.dtos.FeedbackDto;
import com.dutact.web.features.feedback.admin.services.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/feedbacks")
@RequiredArgsConstructor
public class OrganizerStudentFeedbackManagement {
    private final FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<List<FeedbackDto>> getFeedbacks(@RequestParam(name = "eventId") Integer eventId) {
        return ResponseEntity.ok(feedbackService.getFeedbacks(eventId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Integer id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.ok().build();
    }
}
