package com.tuts.auth.controller;

import java.io.IOException;

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
import com.tuts.auth.payload.responses.CustomResponse;
import com.tuts.auth.payload.responses.JwtAuthResponse;
import com.tuts.auth.payload.responses.ResponseHandler;
import com.tuts.auth.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        var tokens = service.authenticate(req);

        res.setAccessToken(tokens.get("access"));
        res.setRefreshToken(tokens.get("refresh"));

        return response.responseBuilder("Success", res, HttpStatus.OK);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<CustomResponse> refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        JwtAuthResponse res = new JwtAuthResponse();
        var tokens = service.refresh(request, response);
        res.setAccessToken(tokens.get("access"));
        res.setRefreshToken(tokens.get("refresh"));

        var results = new CustomResponse("Success", res, HttpStatus.OK);

        return new ResponseEntity<CustomResponse>(results, HttpStatus.OK);
    }
}