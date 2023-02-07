package com.bci.ejercicio.service;

import com.bci.ejercicio.entity.User;
import com.bci.ejercicio.exception.UserAlreadyExists;
import com.bci.ejercicio.mapper.PhoneMapper;
import com.bci.ejercicio.model.LoginResponse;
import com.bci.ejercicio.model.SignUpRequest;
import com.bci.ejercicio.model.SignUpResponse;
import com.bci.ejercicio.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserService {

    private UserRepository userRepository;
    private TextEncryptor textEncryptor;

    private TokenService tokenService;
    private PhoneMapper phoneMapper;

    @Autowired
    public UserService(UserRepository userRepository, TextEncryptor textEncryptor,
                       PhoneMapper phoneMapper, TokenService tokenService) {
        this.userRepository = userRepository;
        this.textEncryptor = textEncryptor;
        this.phoneMapper = phoneMapper;
        this.tokenService = tokenService;
    }

    public SignUpResponse signUp(SignUpRequest request) {

        checkUserAlreadyExists(request.getEmail());

        User user = registerNewUser(request);

        return marshallSignUpResponse(user);
    }

    private SignUpResponse marshallSignUpResponse(User user) {

        return SignUpResponse.builder().id(user.getId())
                .created(user.getCreated())
                .lastLogin(user.getLastLogin())
                .isActive(user.getIsActive())
                .token(tokenService.generateTokenForUser(user.getEmail())).build();
    }

    private void checkUserAlreadyExists(String email) {
        userRepository.findByEmail(email).ifPresent(userFound -> {throw new UserAlreadyExists();});
    }

    private User registerNewUser(SignUpRequest request) {

        return userRepository.save(buildUser(request));

    }

    private User buildUser(SignUpRequest request) {

        LocalDateTime now = LocalDateTime.now();

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(textEncryptor.encrypt(request.getPassword()));
        user.setCreated(now);
        user.setLastLogin(now);
        user.setPhones(phoneMapper.toEntities(request.getPhones(), user));

        return user;
    }

    @Transactional
    public LoginResponse login(String token) {

        User user = userRepository.findByEmail(tokenService.getSubject(token)).orElseThrow(() -> new UsernameNotFoundException("Usuario no existe"));

        LoginResponse response = marshallLoginResponse(user);

        user.setLastLogin(LocalDateTime.now());

        return response;
    }

    private LoginResponse marshallLoginResponse(User user) {

        return LoginResponse.builder().id(user.getId())
                .created(user.getCreated())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .lastLogin(user.getLastLogin())
                .name(user.getName())
                .token(tokenService.generateTokenForUser(user.getEmail()))
                .password(textEncryptor.decrypt(user.getPassword()))
                .phones(phoneMapper.toDtos(user.getPhones())).build();
    }
}
