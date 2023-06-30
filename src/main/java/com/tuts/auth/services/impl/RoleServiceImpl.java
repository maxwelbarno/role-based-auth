package com.tuts.auth.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuts.auth.exceptions.UserNotFoundException;
import com.tuts.auth.models.Role;
import com.tuts.auth.payload.requests.RoleRequest;
import com.tuts.auth.repository.RoleRepository;
import com.tuts.auth.services.RoleService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository rolesDB;

    @Override
    public Role saveRole(RoleRequest req) {
        Role role = Role.build(0, req.getName());
        return rolesDB.save(role);
    }

    @Override
    public List<Role> findRoles() {
        List<Role> list = rolesDB.findAll();
        if (list.isEmpty())
            return null;
        return list;
    }

    @Override
    public Role findOne(String name) throws UserNotFoundException {
        return rolesDB.findRoleByName(name);
    }

}
