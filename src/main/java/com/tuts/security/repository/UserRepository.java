package com.tuts.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tuts.security.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
}
