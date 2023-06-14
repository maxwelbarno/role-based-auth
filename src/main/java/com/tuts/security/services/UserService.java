package com.tuts.security.services;

import org.springframework.stereotype.Service;

import com.tuts.security.models.User;
import com.tuts.security.repository.UserRepository;

@Service
public class UserService {

    UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User user) {
        return repository.save(user);
    }
}
