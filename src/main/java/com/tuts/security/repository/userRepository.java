package com.tuts.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuts.security.models.User;

public interface userRepository extends JpaRepository<User, Integer> {

}
