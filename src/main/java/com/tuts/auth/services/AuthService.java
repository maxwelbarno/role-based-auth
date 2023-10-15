package com.tuts.auth.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tuts.auth.config.security.JwtProvider;
import com.tuts.auth.exceptions.UserNotFoundException;
import com.tuts.auth.models.Token;
import com.tuts.auth.models.User;
import com.tuts.auth.payload.requests.AuthRequest;
import com.tuts.auth.payload.requests.UserRequest;
import com.tuts.auth.repository.TokenRepository;
import com.tuts.auth.repository.UserRepository;
import com.tuts.auth.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    JwtProvider jwtProvider;

    public User register(UserRequest req) {
        User user = User.build(null, req.getName(), req.getUsername(), encoder.encode(req.getPassword()),
                null, null);
        var savedUser = userRepository.save(user);
        return savedUser;
    }

    public Map<String, String> authenticate(AuthRequest req) {
        var user = userRepository.findByUsername(req.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException(null, null));
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        // Revoke active jwt tokens already stored in the DB for the user
        revokeAllUserTokens(user);

        var jwt = jwtProvider.generateJwt(user);
        var refreshJwt = jwtProvider.generateRefreshJwt(user);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access", jwt);
        tokens.put("refresh", refreshJwt);

        saveUserToken(user, jwt);
        return tokens;
    }

    private void saveUserToken(User user, String token) {
        var jwt = Token.build(null, token, null, false, false, user);
        tokenRepository.save(jwt);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validJwts = tokenRepository.findValidTokensByToken(user.getId());
        if (validJwts.isEmpty()) {
            return;
        }
        validJwts.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validJwts);
    }

    public Map<String, String> refresh(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IOException(authHeader, null);
        }

        String refreshJwt = authHeader.substring(7);

        String username = jwtProvider.getUsernameClaim(refreshJwt);

        if (username != null) {
            var user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException(username));

            if (jwtProvider.validateJwtToken(refreshJwt, user)) {
                revokeAllUserTokens(user);
                // Generate Access Token
                var accessToken = jwtProvider.generateJwt(user);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access", accessToken);
                tokens.put("refresh", refreshJwt);
                saveUserToken(user, accessToken);
                return tokens;
            }
        }
        throw new IOException(username, null);
    }

}
