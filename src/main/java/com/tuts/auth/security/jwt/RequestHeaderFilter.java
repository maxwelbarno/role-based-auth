package com.tuts.auth.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tuts.auth.services.impl.UserServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RequestHeaderFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider provider;

    @Autowired
    private UserServiceImpl service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get http token from request
        String jwt = fetchJwt(request);

        // Validate Token
        if (jwt != null && provider.isJwtValid(jwt)) {
            try {
                // Get username from Token
                String username = provider.getUsernameClaim(jwt);

                // Load User from the token claim
                UserDetails userDetails = service.loadUserByUsername(username);

                // Authenticate user
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities());

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Store auth info of the current user
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                log.error("Error: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private String fetchJwt(HttpServletRequest request) {
        String bearerJwt = request.getHeader("Authorization");
        if (bearerJwt != null && bearerJwt.startsWith("Bearer ")) {
            return bearerJwt.substring(7, bearerJwt.length());
        }
        return null;
    }

}