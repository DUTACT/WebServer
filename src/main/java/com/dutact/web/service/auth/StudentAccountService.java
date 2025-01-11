package com.dutact.web.service.auth;

import java.util.Optional;

public interface StudentAccountService {
    Optional<Integer> getStudentId(String username);
}
