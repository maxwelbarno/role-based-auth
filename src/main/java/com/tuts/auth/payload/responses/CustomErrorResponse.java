package com.tuts.auth.payload.responses;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorResponse extends CustomResponse {
    private Map<String, String> errors;

    public CustomErrorResponse(int statusCode, Map<String, String> errors) {
        super(statusCode);
        this.errors = errors;
    }

    public CustomErrorResponse(String message, int statusCode) {
        super(message, statusCode);
    }
}
