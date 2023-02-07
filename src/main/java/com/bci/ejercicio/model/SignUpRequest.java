package com.bci.ejercicio.model;

import com.bci.ejercicio.helper.Constants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class SignUpRequest {

    private String name;

    @NotEmpty(message = "El campo 'email' no debe ser vacío")
    @Pattern(regexp = Constants.MAIL_REGEX, message = "El campo 'email' debe tener un formato válido")
    private String email;

    @Size(min = 8, max = 12, message = "El campo 'password' debe tener minimo {min} caracteres y máximo {max} caracteres")
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "El campo 'password' debe tener una mayúscula y dos números")
    private String password;

    private List<PhoneDto> phones;
}
