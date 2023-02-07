package com.bci.ejercicio.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDto {

    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private Integer codigo;

    private String detail;
}
