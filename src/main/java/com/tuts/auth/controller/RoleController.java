package com.tuts.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuts.auth.payload.requests.RoleRequest;
import com.tuts.auth.payload.responses.ResponseHandler;
import com.tuts.auth.services.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class RoleController {

    @Autowired
    RoleService service;

    @Autowired
    ResponseHandler response;

    @PostMapping("/roles")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<Object> createRole(@RequestBody @Valid RoleRequest req) {
        service.saveRole(req);
        return response.responseBuilder("Role created Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/roles")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<Object> getRoles() {
        return response.responseBuilder("Success", service.findRoles(), HttpStatus.OK);
    }

    @GetMapping("/roles/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<Object> getRoleByName(@PathVariable String name) {
        return response.responseBuilder("Success", service.findOne(name),
                HttpStatus.OK);
    }
}
