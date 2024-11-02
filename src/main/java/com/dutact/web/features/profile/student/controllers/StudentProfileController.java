package com.dutact.web.features.profile.student.controllers;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.post.student.service.PostService;
import com.dutact.web.features.profile.student.dtos.StudentProfileUpdateDto;
import com.dutact.web.features.profile.student.service.StudentProfileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student/profile")
public class StudentProfileController {

    private final StudentProfileService studentProfileService;

    public StudentProfileController(StudentProfileService studentProfileService) {
        this.studentProfileService = studentProfileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(@PathVariable Integer id) {
        return ResponseEntity.ok(studentProfileService.getProfile(id));
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateProfile(
            @PathVariable Integer id,
            @ModelAttribute StudentProfileUpdateDto studentProfileUpdateDto) throws ConflictException, NotExistsException {
        studentProfileService.updateProfile(id, studentProfileUpdateDto);
        return ResponseEntity.ok().build();
    }
}
