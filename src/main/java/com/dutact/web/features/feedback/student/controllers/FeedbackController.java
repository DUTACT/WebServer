package com.dutact.web.features.feedback.student.controllers;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.StudentAccountService;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.feedback.student.dtos.CreateFeedbackDto;
import com.dutact.web.features.feedback.student.dtos.FeedbackDto;
import com.dutact.web.features.feedback.student.dtos.FeedbackQueryParams;
import com.dutact.web.features.feedback.student.dtos.UpdateFeedbackDto;
import com.dutact.web.features.feedback.student.service.FeedbackService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("StudentFeedbackController")
@RequestMapping("/api/student/feedbacks")
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final StudentAccountService studentAccountService;

    public FeedbackController(FeedbackService feedbackService,
                              StudentAccountService studentAccountService) {
        this.feedbackService = feedbackService;
        this.studentAccountService = studentAccountService;
    }

    @GetMapping
    public ResponseEntity<List<FeedbackDto>> getFeedbacks(
            @RequestParam(required = false) Integer eventId,
            @RequestParam(required = false) Integer studentId) {
        var params = new FeedbackQueryParams();
        params.setEventId(eventId);
        params.setStudentId(studentId);

        return ResponseEntity.ok(feedbackService.getFeedbacks(params));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDto> getFeedback(@PathVariable Integer id)
            throws NotExistsException {
        return ResponseEntity.ok(feedbackService.getFeedback(id)
                .orElseThrow(NotExistsException::new));
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FeedbackDto> createFeedback(
            @ModelAttribute CreateFeedbackDto createFeedbackDto) throws NotExistsException {
        var studentId = studentAccountService.getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() -> new RuntimeException("The request is associated with a non-existent student"));

        return ResponseEntity.ok(feedbackService.createFeedback(studentId, createFeedbackDto));
    }

    @PatchMapping(path = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FeedbackDto> updateFeedback(
            @PathVariable Integer id,
            @ModelAttribute UpdateFeedbackDto createFeedbackDto) throws NotExistsException {
        return ResponseEntity.ok(feedbackService.updateFeedback(id, createFeedbackDto));
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "Feedback deleted successfully")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Integer id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }
}
