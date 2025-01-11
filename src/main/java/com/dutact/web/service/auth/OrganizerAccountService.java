package com.dutact.web.service.auth;

import java.util.Optional;

public interface OrganizerAccountService {
    Optional<Integer> getOrganizerId(String username);
}
