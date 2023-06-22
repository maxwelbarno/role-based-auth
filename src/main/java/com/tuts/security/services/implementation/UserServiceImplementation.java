package com.tuts.security.services.implementation;

import java.util.List;

import org.springframework.stereotype.Service;
import com.tuts.security.dto.UserRequest;
import com.tuts.security.exceptions.UserNotFoundException;
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
    public User saveUser(UserRequest userRequest) {
        User user = User.build(0, userRequest.getEmail(), userRequest.getPassword());
        return repository.save(user);
    }

    @Override
    public List<User> getAll() {
        List<User> list = repository.findAll();
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public User getOne(Integer id) throws UserNotFoundException {
        return repository.findById(id).get();
    }

    @Override
    public User update(Integer id, UserRequest userRequest) {
        if (repository.findById(id).isEmpty())
            throw new UserNotFoundException();
        User user = repository.findById(id).get();
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        repository.save(user);
        return user;
    }

    @Override
    public String delete(Integer id) {
        if (repository.findById(id).isEmpty())
            throw new UserNotFoundException();
        repository.deleteById(id);
        return "Success";
    }
}
