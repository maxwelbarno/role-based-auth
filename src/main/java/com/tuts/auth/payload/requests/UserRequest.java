package com.tuts.auth.payload.requests;

import java.util.ArrayList;
import java.util.Collection;

import com.tuts.auth.models.Role;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private Collection<Role> roles = new ArrayList<>();
}
