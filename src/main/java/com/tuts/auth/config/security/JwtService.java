package com.tuts.auth.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${application.jwt-secret}")
    private String jwtSecret;

    @Value("${application.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    @Value("${application.jwt-refresh-expiration-milliseconds}")
    private long jwtRefreshExpirationDate;

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
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtRefreshExpirationDate))
                .sign(getAlgorithm());
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtSecret);
    }

    public boolean isJwtValid(String jwt, UserDetails userDetails) {
        String username = getUsernameClaim(jwt);
        return username.equals(userDetails.getUsername()) && !isJwtExpired(jwt);
    }

    private boolean isJwtExpired(String jwt) {
        return JWT.decode(jwt).getExpiresAt().before(new Date());
    }
}
