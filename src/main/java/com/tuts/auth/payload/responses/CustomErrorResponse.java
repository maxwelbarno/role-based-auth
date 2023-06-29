package com.tuts.auth.payload.responses;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorResponse extends CustomResponse {
    private Map<String, String> errors;

    public CustomErrorResponse(HttpStatus status, Map<String, String> errors) {
        super(status);
        this.errors = errors;
    }

    public CustomErrorResponse(String message, HttpStatus status) {
        super(message, status);
    }
}
