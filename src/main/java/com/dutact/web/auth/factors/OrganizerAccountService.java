package com.dutact.web.auth.factors;

import java.util.Optional;

public interface OrganizerAccountService {
    Optional<Integer> getOrganizerId(String username);
}
