package com.tuts.auth.security.jwt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    @Value("${app.jwt-refresh-expiration-milliseconds}")
    private long jwtRefreshExpirationDate;

    // Generate JWT Token
    public Map<String, String> generateToken(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
        Date refreshExpireDate = new Date(currentDate.getTime() +
                jwtRefreshExpirationDate);

        String access = JWT.create()
                .withSubject(user.getUsername())
                .withClaim("roles", authorities)
                .withExpiresAt(expireDate)
                .sign(getAlgorithm());

        String refresh = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(refreshExpireDate)
                .sign(getAlgorithm());

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access", access);
        tokens.put("refresh", refresh);

        return tokens;
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtSecret.getBytes());
    }

    // Get User From JWT Token
    public String getUsernameClaim(String token) {
        DecodedJWT jwt = JWT
                .require(getAlgorithm())
                .build()
                .verify(token);
        String username = jwt.getSubject();
        return username;
    }

    // Validate JWT Token
    public boolean isJwtValid(String token) {
        try {

            JWTVerifier verifier = JWT
                    .require(getAlgorithm())
                    .build();

            DecodedJWT jwt = verifier.verify(token);

            log.info("Results: {}", jwt);
            return true;
        } catch (SignatureVerificationException e) {
            log.error("Invalid JWT Token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT Claims String is Empty: {}", e.getMessage());
        } catch (AlgorithmMismatchException e) {
            log.error("Error: {}", e.getMessage());
        } catch (TokenExpiredException e) {
            log.error("Error: {}", e.getMessage());
        }
        return false;
    }
}