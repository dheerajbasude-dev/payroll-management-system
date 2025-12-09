package com.dheeraj.payroll.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(
        name = "JwtResponse",
        description = "Schema to hold Jwt token response information"
)
@AllArgsConstructor
@Getter
public class JwtResponse {
    private final String token;
}
