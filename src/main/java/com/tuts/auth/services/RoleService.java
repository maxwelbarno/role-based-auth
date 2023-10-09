package com.tuts.auth.services;

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
public class RoleService {
    @Autowired
    private RoleRepository rolesreRepository;

    public Role save(RoleRequest req) {
        Role role = Role.build(null, req.getName(), null);
        return rolesreRepository.save(role);
    }

    public List<Role> findAll() {
        List<Role> list = rolesreRepository.findAll();
        if (list.isEmpty())
            return null;
        return list;
    }

    public Role findOne(String name) throws UserNotFoundException {
        return rolesreRepository.findRoleByName(name);
    }

}
