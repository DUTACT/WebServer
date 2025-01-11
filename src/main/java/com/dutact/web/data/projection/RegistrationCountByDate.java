package com.dutact.web.data.projection;

import java.time.LocalDateTime;

public interface RegistrationCountByDate {
    LocalDateTime getDate();

    Long getCount();
}
