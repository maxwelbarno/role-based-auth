package com.tuts.auth.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tuts.auth.models.User;

import java.util.Date;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtProvider {

    @Value("${application.jwt-secret}")
    private String jwtSecret;

    @Value("${application.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    @Value("${application.jwt-refresh-expiration-milliseconds}")
    private long jwtRefreshExpirationDate;

    public String generateJwtRefresh(String username) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(expireDate)
                .sign(getAlgorithm());
    }

    public String generateJwt(User user) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
        String authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("name", user.getName())
                .withClaim("roles", authorities)
                .withExpiresAt(expireDate)
                .sign(getAlgorithm());
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