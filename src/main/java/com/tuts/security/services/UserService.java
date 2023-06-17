package com.tuts.security.services;

import java.util.List;
import java.util.Optional;

import com.tuts.security.models.User;

public interface UserService {
    public User saveUser(User user);

    public User getOne(Integer userId);

    public List<User> getAll();

    public Optional<User> update(Integer userId, User user);

    public String delete(Integer userId);
}
