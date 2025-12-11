package com.dheeraj.payroll.controller;

import com.dheeraj.payroll.dto.request.JwtRequest;
import com.dheeraj.payroll.dto.response.ErrorResponse;
import com.dheeraj.payroll.dto.response.JwtResponse;
import com.dheeraj.payroll.security.JwtTokenUtil;
import com.dheeraj.payroll.security.JwtUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Authentication")
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;


    /*---------------------------------Generate token---------------------------*/
    @Operation(
            summary = "Login to API",
            description = "Login to get access to the API"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "HTTP Status Unauthorize error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PostMapping("/token")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest request){

        authenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(request.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails);

        URI location = URI.create("/api/auth/token/");
        return ResponseEntity.created(location).body(new JwtResponse(token));
    }

    @Operation(
            summary = "Logout to API",
            description = "Logout to get access to the API"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status Ok"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "HTTP Status Unauthorize error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String auth) {
        if (auth != null && auth.startsWith("Bearer ")) {
            jwtTokenUtil.invalidateToken(auth.substring(7));
        }
        return ResponseEntity.ok("Logged out");
    }


    private void authenticate(String username, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
