package com.tuts.security.services.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tuts.security.models.User;
import com.tuts.security.repository.UserRepository;
import com.tuts.security.services.UserService;

@Service
public class UserServiceImplementation implements UserService {

    UserRepository repository;

    public UserServiceImplementation(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User saveUser(User user) {

        return repository.save(user);
    }

    @Override
    public List<User> getAll() {
        List<User> list = repository.findAll();
        if (!list.isEmpty()) {
            return list;
        }
        return null;

    }

    @Override
    public User getOne(Integer id) {
        return repository.findById(id).get();
    }

    @Override
    public Optional<User> update(Integer id, User data) {
        Optional<User> dbdetails = repository.findById(id);
        if (dbdetails == null) {
            return null;
        } else {
            User user = dbdetails.get();
            user.setEmail(data.getEmail());
            user.setPassword(data.getPassword());
            repository.save(user);
            return dbdetails;
        }
    }

    @Override
    public String delete(Integer id) {
        Optional<User> dbdetails = repository.findById(id);
        if (dbdetails == null) {
            return null;
        } else {
            repository.deleteById(id);
        }
        return "Success";
    }
}
