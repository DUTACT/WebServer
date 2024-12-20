package com.dutact.web.features.likers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentBasicInfoDto {
    private Integer id;
    private String fullName;
    private String email;
    private String avatarUrl;
}