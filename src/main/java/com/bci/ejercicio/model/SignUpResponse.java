package com.bci.ejercicio.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class SignUpResponse {

    private UUID id;

    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss")
    private LocalDateTime lastLogin;

    private String token;

    private Boolean isActive;

}
