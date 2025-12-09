package com.dheeraj.payroll.exception;

import com.dheeraj.payroll.constants.ApiErrors;
import lombok.Getter;

@Getter
public class InvalidEmployeeDesignationException extends RuntimeException {

    private final ApiErrors error = ApiErrors.INVALID_EMPLOYEE_DESIGNATION;

    public InvalidEmployeeDesignationException(String message) {
        super(message == null ? "Invalid employee data" : message);
    }
}
