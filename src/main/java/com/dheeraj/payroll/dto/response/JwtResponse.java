package com.dheeraj.payroll.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@Schema(
        name = "JwtResponse",
        description = "Schema to hold Jwt token response information"
)
@AllArgsConstructor
public class JwtResponse {
    @JsonProperty("token")
    private final String token;
}
