package com.tuts.auth.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tuts.auth.exceptions.UserNotFoundException;
import com.tuts.auth.models.Role;
import com.tuts.auth.models.User;
import com.tuts.auth.payload.requests.UserRequest;
import com.tuts.auth.repository.RoleRepository;
import com.tuts.auth.repository.UserRepository;
import com.tuts.auth.services.UserService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User data) {
        User user = User.build(null, data.getName(), data.getUsername(), passwordEncoder.encode(data.getPassword()),
                data.getRoles(), null);

        log.info("ROLE", data.getRoles());

        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user", id));
    }

    public User update(Integer id, UserRequest req) {

        User old = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user", id));
        old.setName(req.getName());
        old.setUsername(req.getUsername());
        old.setPassword(req.getPassword());
        return userRepository.save(old);
    }

    public String delete(Integer id) {
        if (userRepository.findById(id).isEmpty())
            throw new UserNotFoundException();
        userRepository.deleteById(id);
        return "Success";
    }

    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Role role = rolesRepository.findRoleByName(roleName);
        user.getRoles().add(role);
    }

}
