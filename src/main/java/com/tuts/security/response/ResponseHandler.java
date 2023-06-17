package com.tuts.security.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class ResponseHandler {

    private ResponseEntity<Object> buildResponseEntity(Response response) {
        return new ResponseEntity<Object>(response, response.getStatus());
    }

    @RequestMapping
    public ResponseEntity<Object> responseBuilder(String message, Object responseObject, HttpStatus httpStatus) {

        Response response = new Response();
        response.setMessage(message);
        response.setData(responseObject);
        response.setStatus(httpStatus);

        return buildResponseEntity(response);
    }

    @RequestMapping
    public ResponseEntity<Object> responseBuilder(String message, HttpStatus httpStatus) {

        Response response = new Response();
        response.setMessage(message);
        response.setStatus(httpStatus);
        return buildResponseEntity(response);
    }

    @RequestMapping
    public ResponseEntity<Object> responseBuilder(Object responseObject, HttpStatus httpStatus) {
        return buildResponseEntity(new Response(responseObject, httpStatus));
    }
}
