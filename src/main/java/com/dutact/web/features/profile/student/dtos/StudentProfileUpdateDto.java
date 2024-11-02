package com.dutact.web.features.profile.student.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class StudentProfileUpdateDto {
    @Nullable
    private String fullName;

    @Nullable
    private String phone;

    @Nullable
    private String faculty;

    @Nullable
    private MultipartFile avatar;
}
