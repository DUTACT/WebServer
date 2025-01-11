package com.dutact.web.controller.profile;

import com.dutact.web.common.api.ErrorMessage;
import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.dto.auth.NewPasswordDto;
import com.dutact.web.dto.profile.student.StudentProfileUpdateDto;
import com.dutact.web.service.auth.exception.InvalidCredentialsException;
import com.dutact.web.service.auth.exception.NoPermissionException;
import com.dutact.web.service.profile.student.StudentProfileService;
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
    public ResponseEntity<?> getProfile(@PathVariable Integer id) throws NotExistsException {
        return ResponseEntity.ok(studentProfileService.getProfile(id));
    }

    @PatchMapping(path = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateProfile(
            @PathVariable Integer id,
            @ModelAttribute StudentProfileUpdateDto studentProfileUpdateDto) throws ConflictException, NotExistsException {
        return ResponseEntity.ok(studentProfileService.updateProfile(id, studentProfileUpdateDto));
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Integer id, @RequestBody NewPasswordDto newPasswordDto) throws NotExistsException {
        try {
            studentProfileService.changePassword(id, newPasswordDto);
            return ResponseEntity.ok().build();
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(400).body(new ErrorMessage(e.getMessage()));
        } catch (NoPermissionException e) {
            return ResponseEntity.status(403).body(new ErrorMessage(e.getMessage()));
        }
    }
}
