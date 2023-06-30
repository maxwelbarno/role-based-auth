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
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository usersDB;

    @Autowired
    RoleRepository rolesDB;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersDB.findUserByUsername(username);
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

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    @Override
    public User saveUser(UserRequest req) {
        User user = User.build(0, req.getName(), req.getUsername(), encoder.encode(req.getPassword()), req.getRoles());
        return usersDB.save(user);
    }

    @Override
    public List<User> getAll() {
        List<User> list = usersDB.findAll();
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public User getOne(Integer id) throws UserNotFoundException {
        return usersDB.findById(id).get();
    }

    @Override
    public User update(Integer id, UserRequest req) {

        if (usersDB.findById(id).isEmpty())
            throw new UserNotFoundException();
        User user = usersDB.findById(id).get();
        user.setName(req.getName());
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
        usersDB.save(user);
        return user;
    }

    @Override
    public String delete(Integer id) {
        if (usersDB.findById(id).isEmpty())
            throw new UserNotFoundException();
        usersDB.deleteById(id);
        return "Success";
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = usersDB.findUserByUsername(username);
        Role role = rolesDB.findRoleByName(roleName);
        user.getRoles().add(role);
    }

}
