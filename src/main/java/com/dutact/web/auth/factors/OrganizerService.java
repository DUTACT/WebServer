package com.dutact.web.auth.factors;

import java.util.Optional;

public interface OrganizerService {
    Optional<Integer> getOrganizerId(String username);
}
