package com.bci.ejercicio.model;

import lombok.Data;

import java.util.List;

@Data
public class Error {

    private List<ErrorDto> errores;
}
