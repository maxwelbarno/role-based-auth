package com.tuts.security.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tuts.security.config.JwtTokenProvider;
import com.tuts.security.dto.AuthRequest;
import com.tuts.security.repository.UserRepository;
import com.tuts.security.services.AuthService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class AuthServiceImplementation implements AuthService {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    JwtTokenProvider jwtTokeProvider;

    @Override
    public String login(AuthRequest req) {
        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokeProvider.generateToken(auth);
        return jwtToken;
    }

}
