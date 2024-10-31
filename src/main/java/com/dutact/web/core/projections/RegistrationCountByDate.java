package com.dutact.web.core.projections;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistrationCountByDate {
    private LocalDate date;
    private Integer count;
}
