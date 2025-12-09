package com.dheeraj.payroll.exception;

import com.dheeraj.payroll.constants.ApiErrors;
import lombok.Getter;

@Getter
public class EmployeeNotFoundException extends RuntimeException {

    private final ApiErrors error = ApiErrors.EMPLOYEE_NOT_FOUND;

    public EmployeeNotFoundException(String message) {
        super(message == null ? "Employee not found" : message);
    }
}
