package com.tuts.auth.payload.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtAuthResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
}
