package com.dutact.web.controller.feedback.admin;

import com.dutact.web.dto.feedback.admin.FeedbackDto;
import com.dutact.web.service.feedback.admin.FeedbackService;
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
