package com.bci.ejercicio.controller;

import com.bci.ejercicio.model.LoginResponse;
import com.bci.ejercicio.model.SignUpRequest;
import com.bci.ejercicio.model.SignUpResponse;
import com.bci.ejercicio.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public SignUpResponse signUp(@RequestBody @Valid SignUpRequest request) {

        return userService.signUp(request);

    }

    @GetMapping("/login")
    public LoginResponse login(@RequestHeader(HttpHeaders.AUTHORIZATION)
                                   @NotNull(message = "El token no debe ser nulo") String token) {

        return userService.login(token.replace("Bearer", ""));
    }
}
