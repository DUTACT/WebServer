package com.dutact.web.controller.profile;

import com.dutact.web.common.api.ErrorMessage;
import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.dto.auth.NewPasswordDto;
import com.dutact.web.dto.profile.admin.OrganizerProfileUpdateDto;
import com.dutact.web.service.auth.exception.InvalidCredentialsException;
import com.dutact.web.service.auth.exception.NoPermissionException;
import com.dutact.web.service.profile.admin.OrganizerProfileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/organizer/{id}/profile")
public class OrganizerProfileController {

    private final OrganizerProfileService organizerProfileService;

    public OrganizerProfileController(OrganizerProfileService organizerProfileService) {
        this.organizerProfileService = organizerProfileService;
    }

    @GetMapping()
    public ResponseEntity<?> getProfile(@PathVariable Integer id) throws NotExistsException {
        return ResponseEntity.ok(organizerProfileService.getProfile(id));
    }

    @PatchMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateProfile(
            @PathVariable Integer id,
            @ModelAttribute OrganizerProfileUpdateDto organizerProfileUpdateDto) throws ConflictException, NotExistsException {
        return ResponseEntity.ok(organizerProfileService.updateProfile(id, organizerProfileUpdateDto));
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Integer id, @RequestBody NewPasswordDto newPasswordDto) throws NotExistsException {
        try {
            organizerProfileService.changePassword(id, newPasswordDto);
            return ResponseEntity.ok().build();
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(400).body(new ErrorMessage(e.getMessage()));
        } catch (NoPermissionException e) {
            return ResponseEntity.status(403).body(new ErrorMessage(e.getMessage()));
        }
    }
}
