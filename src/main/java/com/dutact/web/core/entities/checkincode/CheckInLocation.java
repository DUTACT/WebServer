package com.dutact.web.core.entities.checkincode;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class CheckInLocation {
    @JsonProperty("title")
    private String title;

    @Nullable
    @JsonProperty("address")
    private String address;

    @JsonProperty("geoPosition")
    private GeoPosition geoPosition;
}
