package com.tuts.auth.services;

import java.util.Map;

import com.tuts.auth.payload.requests.AuthRequest;
import com.tuts.auth.payload.requests.TokenRequest;

public interface AuthService {
    Map<String, String> login(AuthRequest req);

    String getRefreshToken(TokenRequest req);
}
