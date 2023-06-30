package com.tuts.auth.services;

import java.util.List;

import com.tuts.auth.models.Role;
import com.tuts.auth.payload.requests.RoleRequest;

public interface RoleService {
    Role saveRole(RoleRequest req);

    List<Role> findRoles();

    Role findOne(String name);

}
