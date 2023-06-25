package com.tuts.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuts.security.dto.AuthRequest;
import com.tuts.security.responses.JwtAuthResponse;
import com.tuts.security.services.AuthService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest req) {
        String jwtToken = service.login(req);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setAccessToken(jwtToken);
        return ResponseEntity.ok(response);
    }
}
