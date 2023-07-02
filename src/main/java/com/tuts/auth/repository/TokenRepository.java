package com.tuts.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuts.auth.models.Token;
import com.tuts.auth.models.User;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findByToken(String token);

    Integer deleteByUser(User user);

}
