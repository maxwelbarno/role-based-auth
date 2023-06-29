package com.tuts.auth.services;

import java.util.Map;

import com.tuts.auth.payload.requests.AuthRequest;

public interface AuthService {
    Map<String, String> login(AuthRequest req);
}
