package com.bci.ejercicio.exception;

import com.bci.ejercicio.model.Error;
import com.bci.ejercicio.model.ErrorDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class Advice extends ResponseEntityExceptionHandler {

    private ErrorDto mapValidationError(ObjectError error) {

        ErrorDto errorDto = new ErrorDto();

        errorDto.setTimestamp(LocalDateTime.now());
        errorDto.setCodigo(HttpStatus.BAD_REQUEST.value());
        errorDto.setDetail(error.getDefaultMessage());

        return errorDto;
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Error handleExpiredTokenException(RuntimeException ex, WebRequest webRequest) {
        Error error = new Error();

        ErrorDto errorDto = new ErrorDto();

        errorDto.setDetail("El token expiró");
        errorDto.setTimestamp(LocalDateTime.now());
        errorDto.setCodigo(HttpStatus.FORBIDDEN.value());

        error.setErrores(List.of(errorDto));

        return error;
    }


    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Error handleSignatureException(RuntimeException ex, WebRequest webRequest) {
        Error error = new Error();

        ErrorDto errorDto = new ErrorDto();

        errorDto.setDetail("Token no válido");
        errorDto.setTimestamp(LocalDateTime.now());
        errorDto.setCodigo(HttpStatus.FORBIDDEN.value());

        error.setErrores(List.of(errorDto));

        return error;
    }

    @ExceptionHandler(UserAlreadyExists.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleUserAlreadyExistsException(RuntimeException ex, WebRequest webRequest) {
        Error error = new Error();

        ErrorDto errorDto = new ErrorDto();

        errorDto.setDetail("El usuario ya existe");
        errorDto.setTimestamp(LocalDateTime.now());
        errorDto.setCodigo(HttpStatus.BAD_REQUEST.value());

        error.setErrores(List.of(errorDto));

        return error;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Error error = new Error();

        List<ErrorDto> list =  ex.getAllErrors().stream().map(this::mapValidationError).collect(Collectors.toList());

        error.setErrores(list);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
