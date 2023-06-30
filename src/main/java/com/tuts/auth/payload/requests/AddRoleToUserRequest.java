package com.tuts.auth.payload.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddRoleToUserRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String roleName;
}
