package com.dheeraj.payroll.exception;

import com.dheeraj.payroll.constants.ApiErrors;
import lombok.Getter;

@Getter
public class LoginException extends RuntimeException {

    private final ApiErrors error = ApiErrors.UNAUTHORIZED_ACCESS;
    private final String token;

    public LoginException(String message, String token) {
        super(message == null ? "Login failed" : message);
        this.token = token;
    }
}
