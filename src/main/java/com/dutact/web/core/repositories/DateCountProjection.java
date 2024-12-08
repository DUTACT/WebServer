package com.dutact.web.core.repositories;

import java.time.LocalDate;

public interface DateCountProjection {
    LocalDate getDate();
    Long getCount();
} 