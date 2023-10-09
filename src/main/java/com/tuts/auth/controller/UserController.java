package com.tuts.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tuts.auth.models.User;
import com.tuts.auth.payload.StatusCodes;
import com.tuts.auth.payload.requests.AddRoleToUserRequest;
import com.tuts.auth.payload.requests.UserRequest;
import com.tuts.auth.payload.responses.CustomResponse;
import com.tuts.auth.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService service;

    @PostMapping("/users/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse createUser(@RequestBody @Valid User userRequest) {
        service.save(userRequest);
        return new CustomResponse("User created Successfully", StatusCodes.CREATED);
    }

    @PostMapping("/users/addRole")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public CustomResponse addRole(@RequestBody @Valid AddRoleToUserRequest req) {
        service.addRoleToUser(req.getUsername(), req.getRoleName());
        return new CustomResponse("Success", StatusCodes.OK);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','SUPERVISOR')")
    public CustomResponse getUsers() {
        return new CustomResponse("Success", service.getAll(), StatusCodes.OK);

    }

    @GetMapping("/users/{id}")
    // @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public CustomResponse getUserById(@PathVariable Integer id) {
        return new CustomResponse("Success", service.findById(id), StatusCodes.OK);

    }

    @PutMapping("/users/{id}")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public CustomResponse updateUser(@PathVariable Integer id, @RequestBody @Valid UserRequest userRequest) {
        service.update(id, userRequest);
        return new CustomResponse("Success", service.update(id, userRequest), StatusCodes.OK);

    }

    @DeleteMapping("/users/{id}")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public CustomResponse deleteUser(@PathVariable Integer id) {
        service.delete(id);
        return new CustomResponse("Success", StatusCodes.OK);

    }
}
