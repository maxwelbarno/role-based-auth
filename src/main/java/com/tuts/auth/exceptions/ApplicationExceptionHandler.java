package com.tuts.auth.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import com.tuts.auth.payload.StatusCodes;
import com.tuts.auth.payload.responses.CustomResponse;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomResponse handleUserNotFoundException(UserNotFoundException exception) {
        return new CustomResponse(exception.getMessage(), StatusCodes.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleNoSuchElementException(NoSuchElementException exception) {
        return new CustomResponse("No Such Element", StatusCodes.BAD_REQUEST);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return new CustomResponse("Required request body is Missing", StatusCodes.BAD_REQUEST);
    }

    @ExceptionHandler(JWTDecodeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CustomResponse handleJWTDecodeException(
            JWTDecodeException exception) {
        return new CustomResponse("Invalid JWT token", StatusCodes.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public CustomResponse handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception) {
        return new CustomResponse("Method Not Supported", StatusCodes.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(EmptyInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleEmptyInputException(EmptyInputException exception) {
        return new CustomResponse("Empty Input", StatusCodes.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleNullPointerException(NullPointerException exception) {
        return new CustomResponse("Null pointer", StatusCodes.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new CustomResponse(errors, StatusCodes.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception) {
        return new CustomResponse("Method argument type mismatch", StatusCodes.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CustomResponse handleUsernameNotFoundException(UsernameNotFoundException exception) {
        return new CustomResponse("Incorrect username provided", StatusCodes.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CustomResponse handleBadCredentialsException(BadCredentialsException exception) {
        return new CustomResponse("Wrong password!", StatusCodes.UNAUTHORIZED);

    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(RefreshTokenException.class)
    public CustomResponse handleRefreshTokenException(
            RefreshTokenException exception) {
        return new CustomResponse(exception.getMessage(), StatusCodes.UNAUTHORIZED);

    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CustomResponse handleAuthenticationException(
            AccessDeniedException exception) {
        return new CustomResponse("Access Denied, you do not have enough privileges to access this resource!",
                StatusCodes.FORBIDDEN);
    }

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CustomResponse handleTokenExpiredException(
            TokenExpiredException exception) {
        return new CustomResponse(exception.getMessage(), StatusCodes.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureVerificationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CustomResponse handleSignatureVerificationException(
            SignatureVerificationException exception) {
        return new CustomResponse("Invalid Token Signature", StatusCodes.UNAUTHORIZED);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CustomResponse handleMissingRequestHeaderException(
            MissingRequestHeaderException exception) {
        return new CustomResponse("Missing Request Header", StatusCodes.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CustomResponse handleAuthenticationException(
            SignatureVerificationException exception) {
        return new CustomResponse("Error", StatusCodes.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtValidationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CustomResponse handleJwtValidationException(
            JwtValidationException exception) {
        return new CustomResponse(exception.getMessage(), StatusCodes.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomResponse handleOtherExceptions(
            Exception exception) {
        return new CustomResponse("Internal server error occured!",
                StatusCodes.INTERNAL_SERVER_ERROR);
    }

}
