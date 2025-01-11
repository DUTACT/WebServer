package com.dutact.web.dto.account;

import com.dutact.web.data.entity.auth.Role;
import lombok.Data;

@Data
public class AccountDto {
    private Integer id;
    private String username;
    private boolean enabled;
    private Role role;
}
