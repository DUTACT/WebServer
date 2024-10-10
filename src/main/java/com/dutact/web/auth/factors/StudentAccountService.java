package com.dutact.web.auth.factors;

import java.util.Optional;

public interface StudentAccountService {
    Optional<Integer> getStudentId(String username);
}
