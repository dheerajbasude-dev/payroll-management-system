package com.dheeraj.payroll.constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ApiErrors {
    UNAUTHORIZED_ACCESS("UNAUTHORIZED"),
    BAD_CREDENTIALS("BAD_CREDENTIALS"),
    USER_DISABLED("USER_DISABLED"),
    VALIDATION_FAILED("VALIDATION_FAILED"),
    EMPLOYEE_NOT_FOUND("EMPLOYEE_NOT_FOUND"),
    INVALID_EMPLOYEE_DESIGNATION("INVALID_EMPLOYEE_DESIGNATION"),
    INVALID_DATE_FORMAT("INVALID_DATE_FORMAT"),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR");


    private final String apiError;

    public String apiError() {
        return apiError;
    }

}
