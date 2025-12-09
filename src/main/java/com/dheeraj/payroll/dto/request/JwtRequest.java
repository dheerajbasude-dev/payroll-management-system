package com.dheeraj.payroll.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(
        name = "JwtRequest",
        description = "Schema to hold Jwt token request information"
)

@NoArgsConstructor
@Getter
public class JwtRequest {

    /*------------------Username------------------*/
    @NotBlank(message = "Username must be required")
    @Schema(
            description = "Username",
            example = "Dheeraj23"
    )
    private String username;


    /*-------------------Password------------------*/
    @NotBlank(message = "Password must be required")
    @Schema(
            description = "Password",
            example = "Dheeraj"
    )
    private String password;

    @Override
    public String toString() {
        return "JwtRequest{ username='" + username + "' }";
        // ❗ Never append password in logs
    }

}
