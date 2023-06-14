package com.tuts.security.services;

import java.util.List;
import java.util.Optional;

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

    public List<User> readAll() {
        return repository.findAll();
    }

    public Optional<User> getOneById(Integer id) {
        return repository.findById(id);
    }

    public User updateOne(Integer id, User data) {
        Optional<User> dbdetails = repository.findById(id);
        if (dbdetails != null) {
            User user = dbdetails.get();
            user.setEmail(data.getEmail());
            user.setPassword(data.getPassword());
            return repository.save(user);

        }
        return null;
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
