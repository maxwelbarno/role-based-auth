package com.tuts.auth.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtProvider {

    @Value("${application.security.jwt.secret-key}")
    private String jwtSecret;

    @Value("${application.security.jwt.expiration}")
    private long expiration;

    @Value("${application.security.jwt.refresh.expiration}")
    private long refreshExpiration;

    public String getUsernameClaim(String jwt) {
        return JWT
                .require(getAlgorithm())
                .build()
                .verify(jwt)
                .getSubject();
    }

    public String generateJwt(UserDetails userDetails) {
        String authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim("authorities", authorities)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(getAlgorithm());
    }

    public String generateRefreshJwt(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshExpiration))
                .sign(getAlgorithm());
    }

    public boolean validateJwtToken(String jwt, UserDetails userDetails) {
        try {
            return getUsernameClaim(jwt).equals(userDetails.getUsername()) &&
                    !isJwtExpired(jwt);
        } catch (JWTVerificationException | IllegalArgumentException e) {

        }
        return false;
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtSecret);
    }

    private boolean isJwtExpired(String jwt) {
        return JWT.decode(jwt).getExpiresAt().before(new Date());
    }

}
