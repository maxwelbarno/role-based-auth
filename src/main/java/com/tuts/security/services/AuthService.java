package com.tuts.security.services;

import com.tuts.security.dto.AuthRequest;

public interface AuthService {
    String login(AuthRequest req);
}
