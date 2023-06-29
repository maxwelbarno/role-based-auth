package com.tuts.auth.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tuts.auth.exceptions.UserNotFoundException;
import com.tuts.auth.models.User;
import com.tuts.auth.payload.requests.UserRequest;
import com.tuts.auth.repository.UserRepository;
import com.tuts.auth.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository repository;

    // @Lazy
    @Autowired
    private PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findUserByEmail(username);
        if (user == null) {
            log.error("User {} not found in the database", username);
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {} ", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // Loop over the roles of the user and create a SimpleGrantAuthority by passing
        // in the role name and add it to the authorities list
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                authorities);
    }

    @Override
    public User saveUser(UserRequest req) {
        User user = User.build(0, req.getEmail(), encoder.encode(req.getPassword()), req.getRoles());
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
