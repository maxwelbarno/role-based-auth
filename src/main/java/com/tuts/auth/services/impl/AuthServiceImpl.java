package com.tuts.auth.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tuts.auth.exceptions.RefreshTokenException;
import com.tuts.auth.models.Token;
import com.tuts.auth.models.User;
import com.tuts.auth.payload.requests.AuthRequest;
import com.tuts.auth.payload.requests.TokenRequest;
import com.tuts.auth.repository.UserRepository;
import com.tuts.auth.security.jwt.JwtProvider;
import com.tuts.auth.services.AuthService;
import com.tuts.auth.services.TokenService;

import lombok.AllArgsConstructor;
import lombok.Data;
@Service
@Data
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository userDB;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    JwtProvider provider;

    @Override
    public Map<String, String> login(AuthRequest req) {
        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        User user = (User) auth.getPrincipal();

        // Generate JWT
        String jwt = provider.generateJwt(user);

        // Get logged in user from DB
        User dbUser = userDB.findUserByUsername(user.getUsername());

        Integer userId = dbUser.getId();

        // Revoke existing user refresh token in the DB
        tokenService.revokeToken(userId);

        String refreshToken = tokenService.generateToken(userId).getToken();

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access", jwt);
        tokens.put("refresh", refreshToken);
        return tokens;
    }

    @Override
    public String getRefreshToken(TokenRequest req) {

        String token = req.getToken();

        if (!token.isEmpty()) {
            try {

                Token storedToken = tokenService.fetchTokenFromDB(token);

                tokenService.verifyTokenExpiry(storedToken);
                User user = storedToken.getUser();
                String username = user.getUsername();
                String refreshToken = provider.generateJwtRefresh(username);
                return refreshToken;
            } catch (RefreshTokenException e) {
                throw new RefreshTokenException(e.getMessage());
            }

        }
        return null;

    }

}