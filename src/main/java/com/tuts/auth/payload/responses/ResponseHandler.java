package com.tuts.auth.payload.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class ResponseHandler {

    private ResponseEntity<Object> buildResponseEntity(CustomResponse response) {
        return new ResponseEntity<Object>(response, response.getStatus());
    }

    @RequestMapping
    public ResponseEntity<Object> responseBuilder(String message, Object responseObject, HttpStatus httpStatus) {
        return buildResponseEntity(new CustomResponse(message, responseObject, httpStatus));
    }

    @RequestMapping
    public ResponseEntity<Object> responseBuilder(String message, HttpStatus httpStatus) {
        return buildResponseEntity(new CustomResponse(message, httpStatus));
    }

    @RequestMapping
    public ResponseEntity<Object> responseBuilder(Object responseObject, HttpStatus httpStatus) {
        return buildResponseEntity(new CustomResponse(responseObject, httpStatus));
    }
}
