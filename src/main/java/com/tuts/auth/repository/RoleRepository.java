package com.tuts.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuts.auth.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByName(String name);
}
