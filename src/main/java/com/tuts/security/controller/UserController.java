package com.tuts.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tuts.security.models.User;
import com.tuts.security.response.ResponseHandler;
import com.tuts.security.services.UserService;

@RestController
public class UserController {
    @Autowired
    UserService service;

    @Autowired
    ResponseHandler response;

    @PostMapping("/api/v1/users")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        service.saveUser(user);
        return response.responseBuilder("User created Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<Object> getUsers() {
        return response.responseBuilder(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/api/v1/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
        return response.responseBuilder(service.getOne(id),
                HttpStatus.OK);
    }

    @PutMapping("/api/v1/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Integer id, @RequestBody User data) {
        service.update(id, data);
        return response.responseBuilder("Success", service.update(id, data), HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        service.delete(id);
        return response.responseBuilder("Success", HttpStatus.OK);
    }
}
