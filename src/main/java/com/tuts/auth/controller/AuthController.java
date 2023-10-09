package com.tuts.auth.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuts.auth.payload.StatusCodes;
import com.tuts.auth.payload.requests.AuthRequest;
import com.tuts.auth.payload.responses.CustomResponse;
import com.tuts.auth.payload.responses.JwtAuthResponse;
import com.tuts.auth.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public CustomResponse authenticate(@RequestBody @Valid AuthRequest req) {
        JwtAuthResponse res = new JwtAuthResponse();
        var tokens = service.authenticate(req);

        res.setAccessToken(tokens.get("access"));
        res.setRefreshToken(tokens.get("refresh"));
        return new CustomResponse("Success", res, StatusCodes.OK);
    }

    @PostMapping("/refresh")
    public CustomResponse refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        JwtAuthResponse res = new JwtAuthResponse();
        var tokens = service.refresh(request, response);
        res.setAccessToken(tokens.get("access"));
        res.setRefreshToken(tokens.get("refresh"));

        return new CustomResponse("Success", res, StatusCodes.OK);
    }
}