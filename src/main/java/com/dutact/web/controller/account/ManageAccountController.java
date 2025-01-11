package com.dutact.web.controller.account;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.dto.account.AccountDto;
import com.dutact.web.dto.account.CreateOrganizerAccountDto;
import com.dutact.web.dto.account.OrganizerAccountDto;
import com.dutact.web.dto.account.StudentAccountDto;
import com.dutact.web.data.entity.auth.Role;
import com.dutact.web.service.account.AccountQueryParams;
import com.dutact.web.service.account.ManageAccountService;
import com.dutact.web.service.account.RoleSpecifiedAccountQueryParams;
import com.dutact.web.service.auth.exception.UsernameOrEmailAlreadyExistException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/manage-accounts")
@AllArgsConstructor
public class ManageAccountController {
    private ManageAccountService manageAccountService;

    @GetMapping()
    public ResponseEntity<PageResponse<AccountDto>> getAllAccount(
            @RequestParam(required = false) @Nullable Role role,
            @RequestParam(required = false) @Nullable String searchQuery,
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer pageSize) {
        AccountQueryParams queryParams = new AccountQueryParams();
        queryParams.setRole(role);
        queryParams.setSearchQuery(searchQuery);
        queryParams.setPage(page);
        queryParams.setPageSize(pageSize);

        return ResponseEntity.ok(manageAccountService.getAllAccount(queryParams));
    }

    @GetMapping("/students")
    public ResponseEntity<PageResponse<StudentAccountDto>> getAllStudentAccount(
            @RequestParam(required = false) @Nullable String searchQuery,
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer pageSize) {
        var queryParams = new RoleSpecifiedAccountQueryParams();
        queryParams.setSearchQuery(searchQuery);
        queryParams.setPage(page);
        queryParams.setPageSize(pageSize);

        return ResponseEntity.ok(manageAccountService.getAllStudentAccount(queryParams));
    }

    @GetMapping("/organizers")
    public ResponseEntity<PageResponse<OrganizerAccountDto>> getAllOrganizerAccount(
            @RequestParam(required = false) @Nullable String searchQuery,
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer pageSize) {
        var queryParams = new RoleSpecifiedAccountQueryParams();
        queryParams.setSearchQuery(searchQuery);
        queryParams.setPage(page);
        queryParams.setPageSize(pageSize);

        return ResponseEntity.ok(manageAccountService.getAllOrganizerAccount(queryParams));
    }

    @PostMapping("/organizers")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = AccountDto.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    content = @Content(schema = @Schema(implementation = CreateAccountFailedResponse.class))
            )
    })
    public ResponseEntity<Object> createOrganizerAccount(@RequestBody CreateOrganizerAccountDto dto) {
        try {
            return ResponseEntity.ok(manageAccountService.createOrganizer(dto));
        } catch (UsernameOrEmailAlreadyExistException e) {
            var response = new CreateAccountFailedResponse();
            response.setStatus(CreateAccountFailedResponse.USERNAME_ALREADY_EXISTS);

            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @PatchMapping("/{accountId}/block")
    public ResponseEntity<Void> blockAccount(@PathVariable Integer accountId) throws NotExistsException {
        manageAccountService.blockAccount(accountId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{accountId}/unblock")
    public ResponseEntity<Void> unblockAccount(@PathVariable Integer accountId) throws NotExistsException {
        manageAccountService.unblockAccount(accountId);
        return ResponseEntity.ok().build();
    }
}
