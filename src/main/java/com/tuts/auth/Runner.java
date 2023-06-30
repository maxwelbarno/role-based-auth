package com.tuts.auth;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tuts.auth.payload.requests.RoleRequest;
import com.tuts.auth.payload.requests.UserRequest;
import com.tuts.auth.services.RoleService;
import com.tuts.auth.services.UserService;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private UserService usersDBService;

    @Autowired
    private RoleService rolesDBservice;

    @Override
    public void run(String... args) throws Exception {
        rolesDBservice.saveRole(new RoleRequest("USER"));
        rolesDBservice.saveRole(new RoleRequest("ADMIN"));
        rolesDBservice.saveRole(new RoleRequest("MANAGER"));

        usersDBService.saveUser(new UserRequest("FAITH KEBUT", "manager", "manager123", new ArrayList<>()));
        usersDBService.saveUser(new UserRequest("MAXWEL BARNO", "admin", "admin123", new ArrayList<>()));
        usersDBService.saveUser(new UserRequest("JOHN DOE", "user", "user123", new ArrayList<>()));

        usersDBService.addRoleToUser("manager", "MANAGER");
        usersDBService.addRoleToUser("admin", "ADMIN");
        usersDBService.addRoleToUser("user", "USER");

    }
}
