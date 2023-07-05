package com.tuts.auth.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuts.auth.exceptions.UserNotFoundException;
import com.tuts.auth.models.Role;
import com.tuts.auth.models.User;
import com.tuts.auth.payload.requests.UserRequest;
import com.tuts.auth.repository.RoleRepository;
import com.tuts.auth.repository.UserRepository;
import com.tuts.auth.services.UserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository usersDB;

    @Autowired
    RoleRepository rolesDB;

    
    public List<User> getAll() {
        List<User> list = usersDB.findAll();
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    
    public User getOne(Integer id) throws UserNotFoundException {
        return usersDB.findById(id).get();
    }

    
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

    
    public String delete(Integer id) {
        if (usersDB.findById(id).isEmpty())
            throw new UserNotFoundException();
        usersDB.deleteById(id);
        return "Success";
    }

    
    public void addRoleToUser(String username, String roleName) {
        User user = usersDB.findUserByUsername(username).orElseThrow();
        Role role = rolesDB.findRoleByName(roleName);
        user.getRoles().add(role);
    }

}
