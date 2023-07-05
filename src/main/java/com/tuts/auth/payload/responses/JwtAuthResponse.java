package com.tuts.auth.payload.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
}
