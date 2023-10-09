package com.tuts.auth.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.auth0.jwt.exceptions.JWTVerificationException;

import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtValidationException extends JWTVerificationException {
    public JwtValidationException(String message) {
        super(message);
    }

    public JwtValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
