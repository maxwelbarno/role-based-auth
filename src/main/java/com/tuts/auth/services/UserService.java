package com.tuts.auth.services;

import java.util.List;

import com.tuts.auth.models.User;
import com.tuts.auth.payload.requests.UserRequest;

public interface UserService {
    public User saveUser(UserRequest userRequest);

    public User getOne(Integer userId);

    public List<User> getAll();

    public User update(Integer userId, UserRequest userRequest);

    public String delete(Integer userId);
}
