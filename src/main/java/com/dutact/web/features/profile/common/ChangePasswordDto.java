package com.dutact.web.features.profile.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import lombok.Data;

@Data
@Schema(name = "ChangePasswordDto")
public class ChangePasswordDto {
    @Nonnull
    @JsonProperty("oldPassword")
    private String oldPassword;

    @Nonnull
    @JsonProperty("newPassword")
    private String newPassword;
}
