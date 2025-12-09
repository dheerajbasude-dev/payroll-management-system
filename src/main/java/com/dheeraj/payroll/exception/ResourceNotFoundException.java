package com.dheeraj.payroll.exception;

import com.dheeraj.payroll.constants.ApiErrors;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final ApiErrors error = ApiErrors.RESOURCE_NOT_FOUND;

    public ResourceNotFoundException(String message) {
        super(message == null ? "No Summary Data Found" : message);
    }
}
