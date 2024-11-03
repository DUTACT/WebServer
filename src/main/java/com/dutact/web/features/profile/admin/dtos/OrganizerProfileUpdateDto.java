package com.dutact.web.features.profile.admin.dtos;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class OrganizerProfileUpdateDto {
    @Nullable
    private String name;

    @Nullable
    private MultipartFile avatar;

    @Nullable
    private String phone;

    @Nullable
    private String address;

    @Nullable
    private String personInChargeName;
}
