package com.dheeraj.payroll.exception;

import com.dheeraj.payroll.constants.ApiErrors;
import com.dheeraj.payroll.dto.response.ErrorResponse;
import com.dheeraj.payroll.utils.TraceIdGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /*------------------LOGIN EXCEPTION--------------------*/
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> handleLoginException(LoginException exception, HttpServletRequest request) {
        String traceId = newTraceId();
        LOGGER.error("[{}] LoginException: {}", traceId, exception.getMessage());
        return buildResponse(HttpStatus.CONFLICT, exception.getError(), exception.getMessage(), request, traceId);
    }


    /*-------------BAD CREDENTIALS EXCEPTION--------------*/
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException exception, HttpServletRequest request) {
        String traceId = newTraceId();
        LOGGER.warn("[{}] BadCredentialsException: {}", traceId, exception.getMessage());
        return buildResponse(HttpStatus.UNAUTHORIZED, ApiErrors.BAD_CREDENTIALS,"Invalid credentials: username or password", request, traceId);
    }


    /*-----------------DISABLED EXCEPTION-------------------*/
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabledException(DisabledException exception, HttpServletRequest request) {
        String traceId = newTraceId();
        LOGGER.error("[{}] DisabledException: {}", traceId, exception.getMessage(), exception);
        return buildResponse(HttpStatus.FORBIDDEN, ApiErrors.USER_DISABLED,"User account is disabled", request, traceId);
    }


    /*---------------- VALIDATION EXCEPTIONS ----------------*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        String traceId = newTraceId();
        String message = exception.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).findFirst().orElse("Validation failed");
        LOGGER.warn("[{}] ValidationError: {}", traceId, message);
        return buildResponse(HttpStatus.BAD_REQUEST, ApiErrors.VALIDATION_FAILED, message, request, traceId);
    }


    /*---------------DATE TIME PARSE EXCEPTION-----------------*/
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponse> handleDateTimeParseException(DateTimeParseException exception, HttpServletRequest request) {
        String traceId = newTraceId();
        LOGGER.warn("[{}] DateTimeParseException: {}", traceId, exception.getMessage(), exception);
        return buildResponse(HttpStatus.BAD_REQUEST, ApiErrors.VALIDATION_FAILED,
                "Invalid date format. Please use YYYY-MM.", request, traceId);
    }


    /*-----------------INVALID DATE EXCEPTION--------------------*/
    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDateException(InvalidDateException exception, HttpServletRequest request) {
        String traceId = newTraceId();
        LOGGER.warn("[{}] InvalidDateException: {}", traceId, exception.getMessage(), exception);
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getError(), exception.getMessage(), request, traceId);
    }


    /*-----------------RESOURCE NOT FOUND EXCEPTION----------------*/
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) {
        String traceId = newTraceId();
        LOGGER.warn("[{}] ResourceNotFoundException: {}", traceId, exception.getMessage(), exception);
        return buildResponse(HttpStatus.NOT_FOUND, exception.getError(), exception.getMessage(), request, traceId);
    }


    /*-----------------EMPLOYEE NOT FOUND EXCEPTION----------------*/
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(EmployeeNotFoundException exception, HttpServletRequest request) {
        String traceId = newTraceId();
        LOGGER.warn("[{}] EmployeeNotFoundException: {}", traceId, exception.getMessage());
        return buildResponse(HttpStatus.NOT_FOUND, exception.getError(), exception.getMessage(), request, traceId);
    }


    /*------------INVALID EMPLOYEE DESIGNATION EXCEPTION--------------*/
    @ExceptionHandler(InvalidEmployeeDesignationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEmployeeDesignationException(InvalidEmployeeDesignationException exception, HttpServletRequest request) {
        String traceId = newTraceId();
        LOGGER.warn("[{}] InvalidEmployeeDesignationException: {}", traceId, exception.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getError(), exception.getMessage(), request, traceId);
    }


    /*--------------------------GENERIC FALLBACK-----------------------*/
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception, HttpServletRequest request) {
        String traceId = newTraceId();
        LOGGER.error("[{}] UnhandledException: {}", traceId, exception.getMessage(), exception);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ApiErrors.INTERNAL_SERVER_ERROR,
                "Something went wrong. Please try again later.", request, traceId);
    }


    /*------------------Generate trace id------------------*/
    private String newTraceId(){
        return TraceIdGenerator.generateTraceId();
    }


    /*---------------Build error response----------------*/
    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, ApiErrors apiErrors, String message, HttpServletRequest request, String traceId) {
        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), apiErrors.apiError(), message, request.getRequestURI(),traceId));
    }

}
