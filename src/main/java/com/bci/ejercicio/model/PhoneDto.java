package com.bci.ejercicio.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PhoneDto {

    private Long number;

    @JsonProperty("citycode")
    private Integer cityCode;

    @JsonProperty("countrycode")
    private String countryCode;
}
