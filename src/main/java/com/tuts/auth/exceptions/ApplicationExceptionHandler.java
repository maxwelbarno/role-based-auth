package com.tuts.auth.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.tuts.auth.payload.responses.CustomErrorResponse;

@ControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        CustomErrorResponse response = new CustomErrorResponse(
                exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);

        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException exception) {

        CustomErrorResponse response = new CustomErrorResponse(
                exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        response.setMessage("Requested Entity does not Exist");

        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        CustomErrorResponse response = new CustomErrorResponse(
                exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        response.setMessage("Required request body is Missing");

        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception) {
        CustomErrorResponse response = new CustomErrorResponse(
                exception.getLocalizedMessage(), HttpStatus.METHOD_NOT_ALLOWED);

        return new ResponseEntity<Object>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<Object> handleEmptyInputException(EmptyInputException exception) {
        CustomErrorResponse response = new CustomErrorResponse(
                exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException exception) {
        CustomErrorResponse response = new CustomErrorResponse(
                exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        CustomErrorResponse response = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST, errors);

        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception) {
        CustomErrorResponse response = new CustomErrorResponse(
                exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        CustomErrorResponse response = new CustomErrorResponse(
                exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException exception) {
        CustomErrorResponse response = new CustomErrorResponse(
                exception.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(
            AccessDeniedException exception) {
        CustomErrorResponse response = new CustomErrorResponse(
                exception.getLocalizedMessage(), HttpStatus.FORBIDDEN);

        return new ResponseEntity<Object>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<Object> handleRefreshTokenException(
            RefreshTokenException exception) {
        CustomErrorResponse response = new CustomErrorResponse(
                exception.getLocalizedMessage(), HttpStatus.FORBIDDEN);

        return new ResponseEntity<Object>(response, HttpStatus.FORBIDDEN);
    }

}
