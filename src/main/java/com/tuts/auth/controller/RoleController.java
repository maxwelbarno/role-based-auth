package com.tuts.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tuts.auth.payload.StatusCodes;
import com.tuts.auth.payload.requests.RoleRequest;
import com.tuts.auth.payload.responses.CustomResponse;
import com.tuts.auth.services.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class RoleController {

    @Autowired
    RoleService service;

    @PostMapping("/roles")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse createRole(@RequestBody @Valid RoleRequest req) {
        service.save(req);
        return new CustomResponse("Role created Successfully", StatusCodes.CREATED);
    }

    @GetMapping("/roles")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public CustomResponse getRoles() {
        return new CustomResponse("Success", service.findAll(), StatusCodes.OK);
    }

    @GetMapping("/roles/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public CustomResponse getRoleByName(@PathVariable String name) {
        return new CustomResponse("Success", service.findOne(name), StatusCodes.OK);

    }
}
