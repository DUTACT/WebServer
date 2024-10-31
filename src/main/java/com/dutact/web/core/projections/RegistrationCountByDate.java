package com.dutact.web.core.projections;

import java.time.LocalDateTime;

public interface RegistrationCountByDate {
    LocalDateTime getDate();

    Long getCount();
}
