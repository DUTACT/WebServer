package com.dutact.web.features.account.dto;

import com.dutact.web.auth.factors.Role;
import lombok.Data;

@Data
public class CreateOrganizerAccountDto {
    private String username;
    private String password;
}
