package com.bci.ejercicio;

import com.bci.ejercicio.entity.User;
import com.bci.ejercicio.exception.UserAlreadyExists;
import com.bci.ejercicio.helper.JsonExamples;
import com.bci.ejercicio.mapper.PhoneMapper;
import com.bci.ejercicio.model.LoginResponse;
import com.bci.ejercicio.model.SignUpRequest;
import com.bci.ejercicio.model.SignUpResponse;
import com.bci.ejercicio.repository.UserRepository;
import com.bci.ejercicio.service.TokenService;
import com.bci.ejercicio.service.UserService;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.util.Optional;

@SpringBootTest(classes = {UserService.class, ObjectMapper.class,
TextEncryptor.class, PhoneMapper.class, TokenService.class})
public class ServiceTests {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TextEncryptor textEncryptor;

    @MockBean
    TokenService tokenService;



    @Test
    void testSignUpWhenUserAlreadyExists() throws Exception {

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExists.class,
                () -> userService.signUp(objectMapper.readValue(JsonExamples.SIGN_UP_REQUEST, SignUpRequest.class)));
    }

    @Test
    void testSignUpWhenUserDoesNotExistReturnsToken() throws Exception {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(new User());
        when(tokenService.generateTokenForUser(any())).thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJob2xhQHlhaG9vLmNvbSIsImV4cCI6MTY3NTc3NzEyN30.zUZyLUElSsqtnQhUZnKd78pBsaHP5OmyliBLHQwbK0k");

        SignUpResponse signUpResponse = userService.signUp(objectMapper.readValue(JsonExamples.SIGN_UP_REQUEST, SignUpRequest.class));

        assertNotNull(signUpResponse.getToken());
    }

    @Test
    void testLoginWhenUserNotFound() throws Exception {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(tokenService.getSubject(anyString())).thenReturn("");
        assertThrows(UsernameNotFoundException.class,
                () -> userService.login(""));
    }



}
