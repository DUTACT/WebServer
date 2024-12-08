package com.dutact.web.core.entities.checkincode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GeoPosition {
    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("lng")
    private Double lng;
}
