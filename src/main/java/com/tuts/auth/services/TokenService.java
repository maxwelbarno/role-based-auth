package com.tuts.auth.services;

import com.tuts.auth.models.Token;

public interface TokenService {

     public Token fetchTokenFromDB(String token);

    public Token generateToken(Integer userId);

    public Token verifyTokenExpiry(Token token);

    public int revokeToken(Integer userId);
}
