package com.dutact.web.features.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrganizerAccountDto extends AccountDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("address")
    private String address;

    @JsonProperty("personInChargeName")
    private String personInChargeName;

    @JsonProperty("avatarUrl")
    private String avatarUrl;
}
