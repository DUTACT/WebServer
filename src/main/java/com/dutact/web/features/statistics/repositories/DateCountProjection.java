package com.dutact.web.features.statistics.repositories;

import java.time.LocalDate;

public interface DateCountProjection {
    LocalDate getDate();
    Long getCount();
} 