package com.dutact.web.data.repository;

import java.time.LocalDate;

public interface DateCountProjection {
    LocalDate getDate();

    Long getCount();
} 