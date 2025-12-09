package com.dheeraj.payroll.exception;

import com.dheeraj.payroll.constants.ApiErrors;
import lombok.Getter;

@Getter
public class InvalidDateException extends RuntimeException {

    private final ApiErrors error = ApiErrors.INVALID_DATE_FORMAT;

    public InvalidDateException(String message) {
        super((message==null) ? "Invalid Date Format" : message);
    }
}
