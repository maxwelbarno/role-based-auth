package com.tuts.auth.DTO;

import java.util.Collection;
import java.util.List;

import com.tuts.auth.models.Role;
import com.tuts.auth.models.Token;

import jakarta.validation.constraints.NotEmpty;

public record UserDto(
        Integer id,
        @NotEmpty(message = "name is required") String name,
        @NotEmpty(message = "username is required") String username,
        Collection<Role> roles, List<Token> tokens) {
}
