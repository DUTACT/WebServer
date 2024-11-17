package com.dutact.web.features.account.dto;

import com.dutact.web.auth.factors.Role;
import lombok.Data;

@Data
public class AccountDto {
    private Integer id;
    private String username;
    private boolean enabled;
    private Role role;
}
