package com.tuts.auth.services.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tuts.auth.payload.requests.AuthRequest;
import com.tuts.auth.repository.UserRepository;
import com.tuts.auth.security.jwt.JwtProvider;
import com.tuts.auth.services.AuthService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    JwtProvider provider;

    @Override
    public Map<String, String> login(AuthRequest req) {
        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        Map<String, String> tokenMap = provider.generateToken(auth);
        return tokenMap;
    }

}