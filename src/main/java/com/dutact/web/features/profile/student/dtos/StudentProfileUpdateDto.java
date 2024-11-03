package com.dutact.web.features.profile.student.dtos;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StudentProfileUpdateDto {
    @Nullable
    private String fullName;

    @Nullable
    private String phone;

    @Nullable
    private String faculty;

    @Nullable
    private String address;

    @Nullable
    private String className;

    @Nullable
    private MultipartFile avatar;
}
