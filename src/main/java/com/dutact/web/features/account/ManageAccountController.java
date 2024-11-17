package com.dutact.web.features.account;

import com.dutact.web.auth.exception.UsernameOrEmailAlreadyExistException;
import com.dutact.web.auth.factors.Role;
import com.dutact.web.common.api.ErrorMessage;
import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.account.dto.AccountDto;
import com.dutact.web.features.account.dto.CreateOrganizerAccountDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer pageSize
    )
    {
        AccountQueryParams queryParams = new AccountQueryParams();
        queryParams.setRole(role);
        queryParams.setSearchQuery(searchQuery);
        queryParams.setPage(page);
        queryParams.setPageSize(pageSize);

        return ResponseEntity.ok(manageAccountService.getAllAccount(queryParams));
    }
    @PostMapping()
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Account created successfully",
                    content = @Content(schema = @Schema(implementation = AccountDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Username or email already exists"
            )
    })
    public ResponseEntity<Object> createOrganizerAccount(@RequestBody CreateOrganizerAccountDto dto){
        try {
            return ResponseEntity.ok(manageAccountService.createOrganizer(dto));
        } catch (UsernameOrEmailAlreadyExistException e){
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{accountId}/block")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Account blocked successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Account not found"
        )
    })
    public ResponseEntity<Void> blockAccount(@PathVariable Integer accountId) {
        try {
            manageAccountService.blockAccount(accountId);
            return ResponseEntity.ok().build();
        } catch (NotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{accountId}/unblock")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Account unblocked successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Account not found"
        )
    })
    public ResponseEntity<Void> unblockAccount(@PathVariable Integer accountId) {
        try {
            manageAccountService.unblockAccount(accountId);
            return ResponseEntity.ok().build();
        } catch (NotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
