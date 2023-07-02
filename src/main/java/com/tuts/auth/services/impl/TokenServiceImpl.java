package com.tuts.auth.services.impl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tuts.auth.exceptions.RefreshTokenException;
import com.tuts.auth.models.Token;
import com.tuts.auth.models.User;
import com.tuts.auth.repository.TokenRepository;
import com.tuts.auth.repository.UserRepository;
import com.tuts.auth.services.TokenService;

import jakarta.transaction.Transactional;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenRepository tokensDB;

    @Autowired
    private UserRepository usersDB;

    @Value("${application.db-stored-token-milliseconds}")
    private Integer tokenDuration;

    public Token fetchTokenFromDB(String token) {
        return tokensDB.findByToken(token);
    }

    public Token generateToken(Integer userId) {
        Token token = new Token();
        User user = usersDB.findById(userId).get();
        token.setUser(user);
        token.setExpiryInstant(Instant.now().plusMillis(tokenDuration));
        token.setToken(UUID.randomUUID().toString());
        tokensDB.save(token);
        return token;
    }

    public Token verifyTokenExpiry(Token token) {
        if (token.getExpiryInstant().compareTo(Instant.now()) < 0) {
            throw new RefreshTokenException("The Refresh Token Expired and was Revoked, please Login Again!");
        }
        return token;
    }

    @Transactional
    public int revokeToken(Integer userId) {
        return tokensDB.deleteByUser(usersDB.findById(userId).get());
    }
}
