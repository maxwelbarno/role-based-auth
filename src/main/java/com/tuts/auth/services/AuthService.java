package com.tuts.auth.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tuts.auth.config.security.JwtService;
import com.tuts.auth.models.Token;
import com.tuts.auth.models.User;
import com.tuts.auth.payload.requests.AuthRequest;
import com.tuts.auth.payload.requests.UserRequest;
import com.tuts.auth.repository.TokenRepository;
import com.tuts.auth.repository.UserRepository;
import com.tuts.auth.services.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository usersDB;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenRepository tokensDB;

    @Autowired
    JwtService jwtService;

    public User register(UserRequest req) {
        User user = User.build(null, req.getName(), req.getUsername(), encoder.encode(req.getPassword()),
                null, null);
        var savedUser = usersDB.save(user);
        return savedUser;
    }

    public String authenticate(AuthRequest req) {
        var user = usersDB.findUserByUsername(req.getUsername()).orElseThrow();
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        // Revoke active jwt tokens already stored in the DB for the user
        revokeAllUserTokens(user);

        var jwt = jwtService.generateJwt(user);
        saveUserToken(user, jwt);
        return jwt;
    }

    private void saveUserToken(User user, String token) {
        var jwt = Token.build(null, token, null, false, false, user);
        tokensDB.save(jwt);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validJwts = tokensDB.findValidTokensByToken(user.getId());
        if (validJwts.isEmpty()) {
            return;
        }
        validJwts.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokensDB.saveAll(validJwts);
    }

}
