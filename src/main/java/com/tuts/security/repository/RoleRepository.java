package com.tuts.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuts.security.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findRoleByName(String name);
}
