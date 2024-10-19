package com.dutact.web.features.feedback.student.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student/feedbacks")
public class FeedbackController {
    @GetMapping
    public String getFeedbacks(@RequestParam(required = false) Integer eventId,
                               @RequestParam(required = false) Integer studentId) {
        return "Feedbacks";
    }
}
