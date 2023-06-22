package com.tuts.security.services;

import java.util.List;

import com.tuts.security.dto.UserRequest;
import com.tuts.security.models.User;

public interface UserService {
    public User saveUser(UserRequest userRequest);

    public User getOne(Integer userId);

    public List<User> getAll();

    public User update(Integer userId, UserRequest userRequest);

    public String delete(Integer userId);
}
