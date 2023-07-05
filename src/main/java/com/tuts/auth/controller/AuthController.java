package com.tuts.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuts.auth.payload.requests.AuthRequest;
import com.tuts.auth.payload.requests.UserRequest;
import com.tuts.auth.payload.responses.JwtAuthResponse;
import com.tuts.auth.payload.responses.ResponseHandler;
import com.tuts.auth.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private ResponseHandler response;

    @PostMapping("/auth/register")
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserRequest userRequest) {
        service.register(userRequest);
        return response.responseBuilder("User created Successfully", HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Object> authenticate(@RequestBody AuthRequest req) {
        JwtAuthResponse res = new JwtAuthResponse();
        res.setAccessToken(service.authenticate(req));

        return response.responseBuilder("Success", res, HttpStatus.OK);
    }
}