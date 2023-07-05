package com.tuts.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tuts.auth.models.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query(value = "SELECT t.* FROM tokens t INNER JOIN users u ON t.user_id = u.id WHERE u.id = :id AND (t.expired = false or t.revoked = false)", nativeQuery = true)
    List<Token> findValidTokensByToken(Integer id);

    Optional<Token> findByToken(String token);

}
