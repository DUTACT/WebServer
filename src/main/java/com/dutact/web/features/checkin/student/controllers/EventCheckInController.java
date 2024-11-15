package com.dutact.web.features.checkin.student.controllers;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.dto.ResponseToken;
import com.dutact.web.auth.factors.StudentAccountService;
import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.checkin.student.dtos.EventCheckInResult;
import com.dutact.web.features.checkin.student.dtos.StudentCheckInDetailDto;
import com.dutact.web.features.checkin.student.dtos.StudentRegistrationDto;
import com.dutact.web.features.checkin.student.services.EventCheckInService;
import com.dutact.web.features.checkin.student.services.exceptions.AlreadyCheckInException;
import com.dutact.web.features.checkin.student.services.exceptions.EarlyCheckInAttemptException;
import com.dutact.web.features.checkin.student.services.exceptions.LateCheckInAttemptException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/event-check-in")
public class EventCheckInController {

    private final StudentAccountService studentAccountService;
    private final EventCheckInService eventCheckInService;

    public EventCheckInController(StudentAccountService studentAccountService,
                                  EventCheckInService eventCheckInService) {

        this.studentAccountService = studentAccountService;
        this.eventCheckInService = eventCheckInService;
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Check-in successful",
                    content = @Content(schema = @Schema(implementation = EventCheckInResult.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - Check-in failed",
                    content = @Content(schema = @Schema(implementation = CheckInFailedResponse.class)))
    })
    public ResponseEntity<?> checkIn(@RequestParam String code) throws NotExistsException {
        var studentId = studentAccountService
                .getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        try {
            return ResponseEntity.ok(eventCheckInService.checkIn(code, studentId));
        } catch (AlreadyCheckInException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new CheckInFailedResponse(CheckInFailedResponse.ALREADY_CHECKED_IN));
        } catch (EarlyCheckInAttemptException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new CheckInFailedResponse(CheckInFailedResponse.EARLY_CHECK_IN));
        } catch (LateCheckInAttemptException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new CheckInFailedResponse(CheckInFailedResponse.LATE_CHECK_IN));
        }
    }

    @GetMapping("/{eventId}/check-in")
    public ResponseEntity<StudentCheckInDetailDto> getCheckInDetail(@PathVariable Integer eventId)
            throws NotExistsException {
        var username = SecurityContextUtils.getUsername();
        return ResponseEntity.ok(eventCheckInService.getCheckInDetail(eventId, username));
    }
}
@Data
@AllArgsConstructor
class CheckInFailedResponse {
    public static final String ALREADY_CHECKED_IN = "already_checked_in";
    public static final String EARLY_CHECK_IN = "early_check_in";
    public static final String LATE_CHECK_IN = "late_check_in";

    @Schema(allowableValues = {
            ALREADY_CHECKED_IN,
            EARLY_CHECK_IN,
            LATE_CHECK_IN})
    private String status;
}

